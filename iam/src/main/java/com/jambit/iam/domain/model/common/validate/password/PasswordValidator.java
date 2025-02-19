package com.jambit.iam.domain.model.common.validate.password;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 5:29 PM
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(password) || password.length() < 8) {
            return false;
        }
        String passwordRegex = "(?=.*[0-9])(?=.*[a-zA-Z]).*";
        return password.matches(passwordRegex);
    }
}

