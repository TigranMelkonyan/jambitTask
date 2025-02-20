package com.jambit.application.service.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 1:50 PM
 */
public class ModelValidator {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    public static <T> void validate(T model) {
        if (model == null) {
            throw new ConstraintViolationException("Model should not be null", Collections.emptySet());
        }
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
