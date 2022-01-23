package com.mwyrzyk.backendmessagingapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
    return handleExceptionInternal(ex, new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
    return handleExceptionInternal(ex, new ErrorResponse(HttpStatus.NOT_FOUND.toString(), ex.getMessage()),
        new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }
}