package lorekeeper.com.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lorekeeper.com.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        try {

            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {

                return createUsernamePasswordToken(jwtService.decodeToken(authHeader.substring(7)));

            }

            throw new BadCredentialsException("Invalid or empty Authorization header");

        } catch (JWTVerificationException e) {

            throw new BadCredentialsException(e.getMessage());

        }

    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordToken(DecodedJWT decodedJWT) {

        String dsUsername = decodedJWT.getSubject();
        List<SimpleGrantedAuthority> authorityList = Arrays.stream(decodedJWT.getClaim("roleList").asArray(String.class)).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(dsUsername, null, authorityList);

    }

}
