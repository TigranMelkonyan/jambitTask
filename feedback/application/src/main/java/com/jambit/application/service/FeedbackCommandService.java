package com.jambit.application.service;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.command.handler.FeedbackCommandHandler;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.feedback.Feedback;
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
public class FeedbackCommandService {

    private final FeedbackCommandHandler feedbackCommandHandler;

    public Feedback create(final CreateFeedbackCommand command) {
        log.info("Processing feedback create command for user with id - {} ", command.getUserId());
        ModelValidator.validate(command);
        return feedbackCommandHandler.handle(command);
    }

    public void deleteById(final UUID id) {
        log.info("Processing feedback delete command with id - {} ", id);
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        feedbackCommandHandler.handle(id);
    }

}
