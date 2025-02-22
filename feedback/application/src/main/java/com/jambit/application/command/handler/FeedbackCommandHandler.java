package com.jambit.application.command.handler;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.mapper.FeedbackMapper;
import com.jambit.domain.common.exception.RecordPersistenceException;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.repository.feedback.FeedbackRepository;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:24 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackCommandHandler {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackTargetRepository feedbackTargetRepository;

    public Feedback handle(final CreateFeedbackCommand command) {
        log.info("Creating feedback with user id - {} ", command.getUserId());
        if (feedbackRepository.existsByUserIdAndTargetId(command.getUserId(), command.getFeedbackTargetId())) {
            throw new RecordPersistenceException(String
                    .format("User with id - %s already has feedback for target with id - %s",
                            command.getUserId(), command.getFeedbackTargetId()));
        }
        Feedback feedback = feedbackMapper.createFeedbackCommandToEntity(command);
        FeedbackTarget feedbackTarget = feedbackTargetRepository.getById(command.getFeedbackTargetId());
        feedback.setFeedbackTarget(feedbackTarget);
        Feedback result = feedbackRepository.save(feedback);
        log.info("Successfully created feedback with user id - {}, result - {}", command.getUserId(), result);
        return result;
    }

    public void handle(final UUID id) {
        log.info("Deleting feedback with id - {} ", id);
        feedbackRepository.deleteById(id);
        log.info("Successfully deleted feedback with id - {}", id);
    }

}
