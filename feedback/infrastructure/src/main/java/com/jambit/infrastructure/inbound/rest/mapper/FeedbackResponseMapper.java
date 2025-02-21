package com.jambit.infrastructure.inbound.rest.mapper;

import com.jambit.domain.feedback.Feedback;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackResponse;
import org.mapstruct.Mapper;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:33 PM
 */
@Mapper(componentModel = "spring")
public interface FeedbackResponseMapper {

    FeedbackResponse feedbackToResponse(Feedback feedback);

}
