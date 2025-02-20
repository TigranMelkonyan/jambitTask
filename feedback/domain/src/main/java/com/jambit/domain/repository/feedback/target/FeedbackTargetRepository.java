package com.jambit.domain.repository.feedback.target;

import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:29 PM
 */
public interface FeedbackTargetRepository {

    FeedbackTarget getById(final UUID id);

    boolean existsByName(final String name);

    FeedbackTarget save(final FeedbackTarget feedbackTarget);
    
    PageModel<FeedbackTarget> getAll(final int page, final int size);
}
