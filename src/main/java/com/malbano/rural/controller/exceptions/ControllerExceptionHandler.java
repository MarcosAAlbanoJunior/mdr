package com.malbano.rural.controller.exceptions;

import com.malbano.rural.service.exception.MethodNotAllowed;
import com.malbano.rural.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({
            ObjectNotFoundException.class
    })
    public ResponseEntity<StandardError> ObjectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        String error = "Object not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<StandardError> database(HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        StandardError err = new StandardError(Instant.now(), status.value(), error, "Falha ao inserir o Objeto", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<StandardError> badRequest(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<StandardError> badRequestCampo(HttpMessageNotReadableException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({MethodNotAllowed.class})
    public ResponseEntity<StandardError> notAlowwedOnboarding(MethodNotAllowed e, HttpServletRequest request) {
        String error = "Insert Error";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({java.lang.IllegalArgumentException.class})
    public ResponseEntity<StandardError> illegalError(java.lang.IllegalArgumentException e, HttpServletRequest request) {
        String error = "Request Error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
