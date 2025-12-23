package io.github.kawajava.GithubUserRepositoryBrowser;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(GithubUserNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    ErrorResponse handleNotFound(GithubUserNotFoundException ex) {
//        return new ErrorResponse(404, ex.getMessage());
//    }

}
