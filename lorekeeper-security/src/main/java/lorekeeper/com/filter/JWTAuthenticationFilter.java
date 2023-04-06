package lorekeeper.com.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lorekeeper.com.domain.JWTAuthenticationToken;
import lorekeeper.com.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private JWTService jwtService;

    private static final NegatedRequestMatcher antPathLogin = new NegatedRequestMatcher(new AntPathRequestMatcher("/auth/**", HttpMethod.POST.name()));
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(antPathLogin, authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (!requiresAuthentication((HttpServletRequest) request, (HttpServletResponse) response)) {

            chain.doFilter(request, response);

            return;

        }

        SecurityContextHolder.getContext().setAuthentication(attemptAuthentication((HttpServletRequest) request, (HttpServletResponse) response));

        chain.doFilter(request, response);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {

            return createJWTAuthToken(jwtService.decodeToken(authHeader.substring(7)));

        }

        throw new BadCredentialsException("Invalid or empty Authorization header");

    }

    private JWTAuthenticationToken createJWTAuthToken(DecodedJWT decodedJWT) {

        List<SimpleGrantedAuthority> authorityList = Collections.singletonList(decodedJWT.getClaim("role").as(SimpleGrantedAuthority.class));

        return new JWTAuthenticationToken(decodedJWT, authorityList);

    }

}
