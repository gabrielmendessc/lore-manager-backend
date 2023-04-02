package lorekeeper.com.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    
    private HandlerExceptionResolver handlerExceptionResolver;

    public ExceptionHandlerFilter(HandlerExceptionResolver handlerExceptionResolver) {

        this.handlerExceptionResolver = handlerExceptionResolver;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            if (Objects.isNull(handlerExceptionResolver.resolveException(request, response, null, e))) {

                throw e;

            }

        }

    }

}
