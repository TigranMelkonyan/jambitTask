package com.jambit.infrastructure.outbound.persistence;

import com.jambit.domain.common.base.ModelStatus;
import com.jambit.domain.common.exception.RecordNotFoundException;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.outbound.persistence.validation.PersistenceErrorProcessor;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
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
public class FeedbackTargetRepositoryAdapter extends PersistenceErrorProcessor implements FeedbackTargetRepository {

    private final FeedbackTargetJpaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public FeedbackTarget getById(final UUID id) {
        FeedbackTarget feedbackTarget = null;
        try {
            feedbackTarget = repository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException(("Feedback target not exists with id: " + id)));
        } catch (Exception e) {
            handlePersistenceException("Retrieving record", e);
        }
        return feedbackTarget;
    }

    @Override
    @Transactional
    public boolean existsByName(final String name) {
        boolean exists = false;
        try {
            exists = repository.existsByName(name);

        } catch (Exception e) {
            handlePersistenceException("Checking record existence", e);
        }
        return exists;
    }

    @Override
    @Transactional
    public FeedbackTarget save(final FeedbackTarget feedbackTarget) {
        FeedbackTarget savedFeedback = null;
        try {
            savedFeedback = repository.save(feedbackTarget);
        } catch (Exception e) {
            handlePersistenceException("Saving record", e);
        }
        return savedFeedback;
    }

    @Override
    @Transactional
    public void delete(final UUID id) {
        try {
            FeedbackTarget target = getById(id);
            target.setStatus(ModelStatus.DELETED);
            repository.save(target);
        } catch (Exception e) {
            handlePersistenceException("Deleting record", e);
        }
    }

    @Override
    @Transactional
    public PageModel<FeedbackTarget> getAll(final int page, final int size) {
        long totalCount = 0;
        Page<FeedbackTarget> feedbackPage = null;
        try {
            Pageable pageable = PageRequest.of(page, size);
            feedbackPage = repository.findAllFeedbackTargets(pageable);
            totalCount = feedbackPage.getTotalElements();
        } catch (Exception e) {
            handlePersistenceException("Get all records", e);
        }
        return new PageModel<>(Objects.requireNonNull(feedbackPage).getContent(), totalCount);
    }
}
