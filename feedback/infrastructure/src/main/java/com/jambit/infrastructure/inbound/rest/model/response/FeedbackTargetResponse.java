package com.jambit.infrastructure.inbound.rest.model.response;

import com.jambit.domain.feedback.TargetType;
import lombok.Data;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:22 PM
 */
@Data
public class FeedbackTargetResponse {

    private UUID id;
    private TargetType targetType;
    private String name;
}
