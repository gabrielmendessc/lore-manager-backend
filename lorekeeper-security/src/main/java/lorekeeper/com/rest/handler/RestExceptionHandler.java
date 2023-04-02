package lorekeeper.com.rest.handler;

import jakarta.servlet.http.HttpServletRequest;
import lorekeeper.com.rest.domain.RestExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestExceptionResponse> authenticationException(BadCredentialsException e, HttpServletRequest request) {

        RestExceptionResponse restExceptionResponse = RestExceptionResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Authentication Failure")
                .message(e.getMessage())
                .path(request.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restExceptionResponse);

    }

}
