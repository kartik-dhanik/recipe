package com.demo_project.demo_project_app.exception;

import com.demo_project.demo_project_app.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientException;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<ErrorResponse> handleWebClientException(WebClientException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("External API Error", ex.getMessage()),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("Invalid Request", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse("Internal Server Error", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}