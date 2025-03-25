package com.jambit.domain.common.exception;

import lombok.Getter;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 2:47 PM
 */
@Getter
public class BusinessRuleViolationException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessRuleViolationException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
}
