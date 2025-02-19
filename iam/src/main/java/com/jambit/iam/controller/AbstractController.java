package com.jambit.iam.controller;

import com.jambit.iam.controller.rest.model.response.error.ErrorResponse;
import com.jambit.iam.domain.model.common.exception.ErrorCode;
import com.jambit.iam.domain.model.common.exception.RecordConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.ServiceUnavailableException;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 1:02 PM
 */
@RestControllerAdvice
public abstract class AbstractController {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RecordConflictException.class)
    @ResponseBody
    protected final ErrorResponse handle(final RecordConflictException e) {
        return new ErrorResponse(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    protected final ErrorResponse handle(final IllegalArgumentException e) {
        return new ErrorResponse(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseBody
    protected final ErrorResponse handle(final ServiceUnavailableException e) {
        return new ErrorResponse(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    protected final ErrorResponse handle(final ConstraintViolationException e) {
        return new ErrorResponse(ErrorCode.WRONG_PASSWORD, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }

    protected <T> ResponseEntity<T> respondOK(final T object) {
        return respond(object);
    }

    protected <T> ResponseEntity<T> respondEmpty() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private <T> ResponseEntity<T> respond(final T object) {
        return object == null ? respondEmpty() : new ResponseEntity<>(object, HttpStatus.OK);
    }
}
