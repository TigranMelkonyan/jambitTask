package com.jambit.application.service;

import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.application.command.handler.FeedbackTargetCommandHandler;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.feedback.FeedbackTarget;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 1:24 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackTargetCommandService {

    private final FeedbackTargetCommandHandler feedbackTargetCommandHandler;

    public FeedbackTarget create(final CreateFeedbackTargetCommand command) {
        log.info("Processing feedback target create command for target type id - {} ", command.getTargetType());
        ModelValidator.validate(command);
        return feedbackTargetCommandHandler.handle(command);
    }

    public void delete(final UUID id) {
        log.info("Processing feedback target delete command for with id - {} ", id);
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        feedbackTargetCommandHandler.handle(id);
    }

}
