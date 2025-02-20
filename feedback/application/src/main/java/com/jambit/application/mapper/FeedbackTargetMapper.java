package com.jambit.application.mapper;

import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.domain.feedback.FeedbackTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:52 PM
 */
@Mapper(componentModel = "spring")
public interface FeedbackTargetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "deletedOn", ignore = true)
    @Mapping(target = "status", ignore = true)
    FeedbackTarget createFeedbackTargetCommandToEntity(CreateFeedbackTargetCommand command);
}
