package lorekeeper.com.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lorekeeper.com.security.utils.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthorizationFilterLore extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!request.getServletPath().equals("/login") && !request.getServletPath().equals("/token/refresh")) {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {

                try {

                    DecodedJWT decodedJWT = jwtService.decodeToken(authorizationHeader.replace("Bearer ", ""));

                    jwtService.authenticate(decodedJWT);

                } catch (Exception e) {

                    writeError(response, e);

                }

            }
        }

        filterChain.doFilter(request, response);

    }

    private void writeError(HttpServletResponse response, Exception e) throws IOException {

        response.setHeader("error", e.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new HashMap<>();
        body.put("error_message", e.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), body);

    }
}
