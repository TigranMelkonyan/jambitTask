package com.jambit.iam.service.validator;

import com.jambit.iam.domain.model.common.validate.ValidatableModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 11:36 PM
 */
@Component
@RequiredArgsConstructor
public class ModelValidator {

    private final Validator validator;

    public <T extends ValidatableModel> void validate(T model) {
        if (model == null) {
            throw new ConstraintViolationException("Model should not be null", Collections.emptySet());
        }
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
