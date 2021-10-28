package vn.alpaca.userservice.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.alpaca.userservice.dto.wrapper.ErrorResponse;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse
    handleUnauthorizedException(AccessDeniedException exception) {
        ErrorResponse response = new ErrorResponse();

        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(ObjectUtils.isEmpty(exception.getMessage())
                ? "Unauthorized"
                : exception.getMessage()
        );

        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse
    handleNotFoundException(ResourceNotFoundException exception) {
        ErrorResponse response = new ErrorResponse();

        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ObjectUtils.isEmpty(exception.getMessage())
                ? "Resource not found"
                : exception.getMessage()
        );

        return response;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(TokenExpiredException.class)
    public ErrorResponse
    handleRefreshTokenException(TokenExpiredException exception) {
        ErrorResponse response = new ErrorResponse();

        response.setStatusCode(HttpStatus.FORBIDDEN.value());
        response.setMessage(exception.getMessage());

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse
    handleException(Exception exception) {
        ErrorResponse response = new ErrorResponse();

        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());

        return response;
    }
}

