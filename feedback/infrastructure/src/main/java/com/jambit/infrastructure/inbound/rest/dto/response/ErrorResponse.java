package com.jambit.infrastructure.inbound.rest.dto.response;

import com.jambit.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:09 PM
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final ErrorCode errorCode;
    private final String message;
}
