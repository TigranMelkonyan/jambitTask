package com.jambit.infrastructure.inbound.rest.mapper;

import com.jambit.domain.feedback.Feedback;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:33 PM
 */
@Mapper(componentModel = "spring")
public interface FeedbackResponseMapper {

    @Mapping(source = "feedbackTarget.id", target = "feedbackTargetId")
    FeedbackResponse feedbackToResponse(Feedback feedback);

}
