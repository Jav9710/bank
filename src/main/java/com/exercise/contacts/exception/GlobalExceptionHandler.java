package com.exercise.contacts.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleRespourceExceptions(ResourceException resourceException,
                                                                  WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                resourceException.getMessage(),
                resourceException.getHttpStatus(),
                new Date(),
                UUID.randomUUID());
        return new ResponseEntity<>(errorDetails, resourceException.getHttpStatus());

    }

}
