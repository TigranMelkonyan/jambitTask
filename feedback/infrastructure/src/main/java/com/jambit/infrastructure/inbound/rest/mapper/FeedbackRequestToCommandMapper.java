package com.jambit.infrastructure.inbound.rest.mapper;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.infrastructure.inbound.rest.model.request.CreateFeedbackRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:48 PM
 */
@Mapper(componentModel = "spring")
public interface FeedbackRequestToCommandMapper {
    
    @Mapping(target = "userId", ignore = true)
    CreateFeedbackCommand createFeedbackCommand(CreateFeedbackRequest request);
}
