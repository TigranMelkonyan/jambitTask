package com.jambit.application.command.handler;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.mapper.FeedbackMapper;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackDomainService;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.repository.feedback.FeedbackRepository;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackDomainService feedbackDomainService;
    private final FeedbackTargetRepository feedbackTargetRepository;

    @Transactional
    public Feedback handle(final CreateFeedbackCommand command) {
        log.info("Creating feedback with user id - {} ", command.getUserId());
        ModelValidator.validate(command);
        feedbackDomainService.validateFeedbackSubmission(command.getUserId(), command.getFeedbackTargetId());
        Feedback feedback = feedbackMapper.createFeedbackCommandToEntity(command);
        FeedbackTarget feedbackTarget = feedbackTargetRepository.getById(command.getFeedbackTargetId());
        feedback.setFeedbackTarget(feedbackTarget);
        Feedback.validateContent(feedback);
        Feedback result = feedbackRepository.save(feedback);
        log.info("Successfully created feedback with user id - {}, result - {}", command.getUserId(), result);
        return result;
    }

    @Transactional
    public void handle(final UUID id) {
        log.info("Deleting feedback with id - {} ", id);
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        feedbackRepository.deleteById(id);
        log.info("Successfully deleted feedback with id - {}", id);
    }
    
    

}
