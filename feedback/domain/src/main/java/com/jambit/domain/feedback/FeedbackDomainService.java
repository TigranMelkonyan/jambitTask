package com.jambit.domain.feedback;

import com.jambit.domain.common.exception.BusinessRuleViolationException;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 3/31/25
 * Time: 2:28 PM
 */
public interface FeedbackDomainService {

    //This defines business rule that user can create only on feedback per target 
    void validateFeedbackSubmission(final UUID userId, final UUID targetId) throws BusinessRuleViolationException;
}
