package com.jambit.infrastructure.inbound.rest.controller;

import com.jambit.domain.common.exception.ErrorCode;
import com.jambit.domain.common.exception.RecordConflictException;
import com.jambit.infrastructure.inbound.rest.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
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
 * Date: 2/20/25
 * Time: 8:08 PM
 */
@RestControllerAdvice
public abstract class AbstractController {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RecordConflictException.class)
    @ResponseBody
    protected final ErrorResponse handle(final RecordConflictException e) {
        return new ErrorResponse(ErrorCode.RECORD_CONFLICT, e.getMessage());
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
        return new ErrorResponse(ErrorCode.SERVICE_EXCEPTION, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    protected final ErrorResponse handle(final ConstraintViolationException e) {
        return new ErrorResponse(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OAuth2IntrospectionException.class)
    @ResponseBody
    protected final ErrorResponse handle(final OAuth2IntrospectionException e) {
        return new ErrorResponse(ErrorCode.INVALID_CREDENTIALS, e.getMessage());
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
    
}
