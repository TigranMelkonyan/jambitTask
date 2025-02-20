package com.jambit.application.command.handler;

import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.application.mapper.FeedbackTargetMapper;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:24 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackTargetCommandHandler {

    private final FeedbackTargetRepository repository;
    private final FeedbackTargetMapper mapper;

    public FeedbackTarget handle(final CreateFeedbackTargetCommand command) {
        log.info("Creating feedback target with target type- {} ", command.getTargetType());
        FeedbackTarget feedbackTarget = mapper.createFeedbackTargetCommandToEntity(command);
        FeedbackTarget result = repository.save(feedbackTarget);
        log.info("Successfully created feedback target, result - {}", result);
        return result;
    }

}
