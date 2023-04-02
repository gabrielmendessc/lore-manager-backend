package lorekeeper.com.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTService {

    private static final String SECRET_KEY = "RandomKey";

    public String createAcessToken(UserDetails user, String dsIssuer, Integer nrMinutes) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(dsIssuer)
                .withClaim("roleList", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + nrMinutes * 60 * 1000))
                .sign(Algorithm.HMAC256(SECRET_KEY));

    }

    public String createRefreshToken(UserDetails user, String dsIssuer, Integer nrMinutes) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(dsIssuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + nrMinutes * 60 * 1000))
                .sign(Algorithm.HMAC256(SECRET_KEY));

    }

    public DecodedJWT decodeToken(String dsToken) {

        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(dsToken);

    }

}
