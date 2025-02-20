package com.jambit.domain.common.exception;

import lombok.Getter;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 9:09 PM
 */
@Getter
public class RecordPersistenceException extends RecordConflictException {

    public RecordPersistenceException(final String message) {
        super(message, ErrorCode.RECORD_CONFLICT);
    }
}
