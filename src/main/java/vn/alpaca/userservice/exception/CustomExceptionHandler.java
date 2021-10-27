package vn.alpaca.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.alpaca.userservice.dto.response.ErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleNotFoundException(ResourceNotFoundException exception) {
        ErrorResponse response = new ErrorResponse();

        response.setErrorCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(
                ObjectUtils.isEmpty(exception.getMessage())
                        ? "Resource not found"
                        : exception.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(Exception exception) {
        ErrorResponse response = new ErrorResponse();

        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

