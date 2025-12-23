package io.github.kawajava.GithubUserRepositoryBrowser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotExistingUserException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(NotExistingUserException exception) {
        var response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    record ErrorResponse(int status, String message) {
    }
}
