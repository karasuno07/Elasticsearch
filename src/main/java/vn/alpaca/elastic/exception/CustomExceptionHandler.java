package vn.alpaca.elastic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>>
    handleNotFoundException(ResourceNotFoundException exception) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", HttpStatus.NOT_FOUND.value());
        map.put("message", exception.getMessage());
        map.put("timestamp", new Date());

        return new ResponseEntity<>(
                map,
                HttpStatus.NOT_FOUND
        );
    }
}
