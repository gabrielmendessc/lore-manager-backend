package loremanager.com.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import loremanager.com.security.domain.Token;
import loremanager.com.security.utils.JWTUtils;
import loremanager.com.security.utils.UserUtils;
import loremanager.com.service.UserLoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private UserLoreService userLoreService;

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {

            try {

                String dsRefreshToken = authorizationHeader.replace("Bearer ", "");
                DecodedJWT decodedJWT = JWTUtils.decodeToken(dsRefreshToken);
                User user = UserUtils.fromUserLore(userLoreService.findByDsUsername(decodedJWT.getSubject()));
                String dsAcessToken = JWTUtils.createAcessToken(user, request.getRequestURI(), 10);

                Token token = new Token(dsAcessToken, dsRefreshToken);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), token);

            } catch (Exception e) {

                writeError(response, e);

            }

        } else {

            throw new RuntimeException("Refresh token is missing");

        }

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
