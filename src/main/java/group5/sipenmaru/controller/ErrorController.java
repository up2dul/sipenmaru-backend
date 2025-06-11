package group5.sipenmaru.controller;

import group5.sipenmaru.model.WebResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<WebResponse<String>> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.<String>builder()
                        .data(null)
                        .success(false)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseStatusExceptionHandler(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(WebResponse.<String>builder()
                        .data(null)
                        .success(false)
                        .message(exception.getMessage())
                        .build());
    }
}
