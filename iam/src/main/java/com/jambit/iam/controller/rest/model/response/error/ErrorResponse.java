package com.jambit.iam.controller.rest.model.response.error;

import com.jambit.iam.domain.model.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 1:05 PM
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    
    private final ErrorCode errorCode;
    private final String message;
}
