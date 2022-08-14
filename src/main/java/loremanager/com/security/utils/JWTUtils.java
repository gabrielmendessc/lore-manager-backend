package loremanager.com.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class JWTUtils {

    private static Algorithm algorithm = Algorithm.HMAC512("Progamador Drogado".getBytes());

    public static String createAcessToken(User user, String dsIssuer, Integer minutesExpiration) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + minutesExpiration * 60 * 1000))
                .withIssuer(dsIssuer)
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);

    }

    public static String createRefreshToken(User user, String dsIssuer, Integer minutesExpiration) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + minutesExpiration * 60 * 1000))
                .withIssuer(dsIssuer)
                .sign(algorithm);

    }

    public static DecodedJWT decodeToken(String dsToken) {

        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(dsToken);

    }

    public static void authenticate(DecodedJWT decodedJWT) {

        String dsUsername = decodedJWT.getSubject();
        String[] roleArray = decodedJWT.getClaim("roles").asArray(String.class);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        stream(roleArray).forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority(role));
        });

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dsUsername, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }


}
