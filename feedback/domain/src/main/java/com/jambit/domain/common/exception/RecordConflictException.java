package com.jambit.domain.common.exception;

import lombok.Getter;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 2:47 PM
 */
@Getter
public class RecordConflictException extends RuntimeException {

    private final ErrorCode errorCode;

    public RecordConflictException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
}
