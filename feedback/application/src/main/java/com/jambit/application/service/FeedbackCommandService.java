package com.jambit.application.service;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.command.handler.FeedbackCommandHandler;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.domain.feedback.Feedback;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 1:24 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackCommandService {

    private final FeedbackCommandHandler feedbackCommandHandler;
    private final ModelValidator modelValidator;

    public Feedback create(final CreateFeedbackCommand command) {
        log.info("Processing feedback create command for user with id - {} ", command.getUserId());
        modelValidator.validate(command);
        return feedbackCommandHandler.handle(command);
    }

    public void deleteByUser(final UUID userId) {
        log.info("Processing feedback delete command for user with id - {} ", userId);
        Assert.notNull(userId, "userId must not be null");
        feedbackCommandHandler.handle(userId);
    }

}
