package com.jambit.infrastructure.inbound.rest.mapper;

import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.infrastructure.inbound.rest.dto.request.CreateFeedbackTargetRequest;
import org.mapstruct.Mapper;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:48 PM
 */
@Mapper(componentModel = "spring")
public interface FeedbackTargetRequestToCommandMapper {
    
    CreateFeedbackTargetCommand createFeedbackTargetCommand(CreateFeedbackTargetRequest request);
}
