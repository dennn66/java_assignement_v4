package com.dennn66.tasktracker.controllers;


import com.dennn66.gwt.common.ErrorDto;
import com.dennn66.tasktracker.exceptions.RestResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionController {
    @ExceptionHandler
    public ResponseEntity<?> exceptionInterceptor(RestResourceException exc) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
