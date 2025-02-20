package com.jambit.infrastructure.persistence;

import com.jambit.domain.common.exception.RecordNotFoundException;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.repository.feedback.FeedbackRepository;
import com.jambit.domain.repository.feedback.PersistenceErrorProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 2:36 PM
 */
@Component
@RequiredArgsConstructor
public class FeedbackRepositoryAdapter extends PersistenceErrorProcessor implements FeedbackRepository {

    private final FeedbackJpaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Feedback findById(final UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(("Feedback not exists with  id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Feedback getByUserId(final UUID userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new RecordNotFoundException(("Feedback not exists with user id: " + userId)));
    }

    @Override
    @Transactional
    public Feedback save(final Feedback feedback) {
        Feedback savedFeedback = null;
        try {
            savedFeedback = repository.save(feedback);
        } catch (Exception e) {
            handlePersistenceException("Saving record", e);
        }
        return savedFeedback;
    }

    @Override
    @Transactional
    public void deleteByUserId(final UUID userId) {
        try {
            repository.deleteByUserId(userId);
        } catch (Exception e) {
            handlePersistenceException("Delete record", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserId(final UUID userId) {
        return repository.existsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<Feedback> getAllByFeedbackTargetId(final UUID targetId, final int page, final int size) {
        long totalCount = 0;
        Page<Feedback> feedbackPage = null;
        try {
            Pageable pageable = PageRequest.of(page, size);
            feedbackPage = repository.findAllForFeedbackTarget(targetId, pageable);
            totalCount = feedbackPage.getTotalElements();
        } catch (Exception e) {
            handlePersistenceException("Get all records for target id" + targetId, e);
        }
        return new PageModel<>(Objects.requireNonNull(feedbackPage).getContent(), totalCount);
    }

}
