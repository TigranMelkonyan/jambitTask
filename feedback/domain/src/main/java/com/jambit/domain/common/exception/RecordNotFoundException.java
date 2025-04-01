package com.jambit.domain.common.exception;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 9:09 PM
 */
public class RecordNotFoundException extends RecordConflictException {

    public RecordNotFoundException(final String message) {
        super(message, ErrorCode.RECORD_CONFLICT);
    }
}
