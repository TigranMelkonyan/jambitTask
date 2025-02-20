package com.jambit.application.service;

import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.application.command.handler.FeedbackTargetCommandHandler;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.domain.feedback.FeedbackTarget;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
    private final ModelValidator modelValidator;

    public FeedbackTarget create(final CreateFeedbackTargetCommand command) {
        log.info("Processing feedback target create command for target type id - {} ", command.getTargetType());
        modelValidator.validate(command);
        return feedbackTargetCommandHandler.handle(command);
    }

}
