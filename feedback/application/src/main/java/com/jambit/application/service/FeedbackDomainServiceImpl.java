package com.jambit.application.service;

import com.jambit.domain.common.exception.BusinessRuleViolationException;
import com.jambit.domain.common.exception.ErrorCode;
import com.jambit.domain.feedback.FeedbackDomainService;
import com.jambit.domain.repository.feedback.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 3/31/25
 * Time: 2:39 PM
 */
@Service
@AllArgsConstructor
public class FeedbackDomainServiceImpl implements FeedbackDomainService {

    private FeedbackRepository feedbackRepository;

    @Override
    public void validateFeedbackSubmission(final UUID userId, final UUID targetId) {
        if (feedbackRepository.existsByUserIdAndTargetId(userId, targetId)) {
            throw new BusinessRuleViolationException(String
                    .format("User with id - %s already has feedback for target with id - %s",
                            userId, targetId), ErrorCode.RULE_EXCEPTION);
        }
    }
}
