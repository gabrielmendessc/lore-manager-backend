package loremanager.com.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import loremanager.com.domain.UserLore;
import loremanager.com.security.domain.Token;
import loremanager.com.security.utils.JWTService;
import loremanager.com.service.UserLoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


public class AuthenticationFilterLore extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserLoreService userLoreService;
    @Autowired
    private JWTService jwtService;

    public AuthenticationFilterLore(AuthenticationManager authenticationManager){

        this.authenticationManager = authenticationManager;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username.indexOf("@") > 0) {

            username = getUsernameByEmail(username);

        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);

    }

    private String getUsernameByEmail(String username) {
        UserLore userLore = userLoreService.findByDsEmail(username);
        if (Objects.nonNull(userLore)) {

            return userLore.getDsUsername();

        }

        return username;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();

        String acessToken = jwtService.createAcessToken(user, request.getRequestURI(), 10);
        String refreshToken = jwtService.createRefreshToken(user, request.getRequestURI(), 30);

        Token token = new Token(acessToken, refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), token);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        super.unsuccessfulAuthentication(request, response, failed);

    }
}
