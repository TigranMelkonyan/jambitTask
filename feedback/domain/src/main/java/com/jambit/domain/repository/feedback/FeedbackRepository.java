package com.jambit.domain.repository.feedback;

import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:26 PM
 */
public interface FeedbackRepository {
    
    Feedback findById(UUID id);

    Feedback getByUserId(UUID userId);

    Feedback save(Feedback feedback);

    void deleteByUserId(UUID userId);
    
    boolean existsByUserId(UUID userId);

    PageModel<Feedback> getAllByFeedbackTargetId(final UUID targetId, final int page, final int size);
}
