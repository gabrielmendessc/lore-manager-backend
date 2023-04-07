package lorekeeper.com.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lorekeeper.com.domain.UserSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTService {

    @Value("${jwt.token.secret}")
    private String SECRET_KEY;

    public String createAcessToken(UserSecurity user, String issuer, Integer minutes) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0))
                .withClaim("idUser", user.getUserLoreKeeper().getIdUserLoreKeeper())
                .withExpiresAt(new Date(System.currentTimeMillis() + minutes * 60 * 1000))
                .sign(Algorithm.HMAC256(SECRET_KEY));

    }

    public String createRefreshToken(UserSecurity user, String issuer, Integer minutes) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + minutes * 60 * 1000))
                .sign(Algorithm.HMAC256(SECRET_KEY));

    }

    public DecodedJWT decodeToken(String token) {

        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);

    }

}
