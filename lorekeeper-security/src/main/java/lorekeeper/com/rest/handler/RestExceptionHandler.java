package lorekeeper.com.rest.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import lorekeeper.com.rest.domain.RestExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestExceptionResponse> uncataloguedException(Exception e, HttpServletRequest request) {

        return createResponse(e, request.getServletPath(), HttpStatus.INTERNAL_SERVER_ERROR, "Uncatalogued Error");

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestExceptionResponse> notFoundException(Exception e, HttpServletRequest request) {

        return createResponse(e, request.getServletPath(), HttpStatus.NOT_FOUND, "Not Found");

    }

    @ExceptionHandler({JWTVerificationException.class, AuthenticationException.class})
    public ResponseEntity<RestExceptionResponse> authenticationException(Exception e, HttpServletRequest request) {

        return createResponse(e, request.getServletPath(), HttpStatus.FORBIDDEN, "Unable to Authenticate");

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestExceptionResponse> accessDeniedException(Exception e, HttpServletRequest request) {

        return createResponse(e, request.getServletPath(), HttpStatus.FORBIDDEN, "Access Denied");

    }


    private ResponseEntity<RestExceptionResponse> createResponse(Exception e, String dsPath, HttpStatus httpStatus, String dsError) {

        RestExceptionResponse restExceptionResponse = RestExceptionResponse.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .error(dsError)
                .message(e.getMessage())
                .path(dsPath)
                .build();

        return ResponseEntity.status(httpStatus).body(restExceptionResponse);

    }

}
