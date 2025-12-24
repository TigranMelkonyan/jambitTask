package com.jambit.infrastructure.outbound.persistence;

import com.jambit.domain.common.base.ModelStatus;
import com.jambit.domain.common.exception.RecordNotFoundException;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackTargetEntity;
import com.jambit.infrastructure.outbound.persistence.mapper.FeedbackTargetEntityMapper;
import com.jambit.infrastructure.outbound.persistence.validation.PersistenceErrorProcessor;
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
        FeedbackTargetEntity entity = repository.findByIdAndAuditStatus(id)
                .orElseThrow(() -> new RecordNotFoundException(("Feedback target not exists with id: " + id)));
        return FeedbackTargetEntityMapper.toModel(entity);
    }

    @Override
    @Transactional
    public boolean existsByName(final String name) {
        return repository.existsByName(name);
    }

    @Override
    @Transactional
    public FeedbackTarget save(final FeedbackTarget feedbackTarget) {
        FeedbackTarget savedFeedback = null;
        try {
            FeedbackTargetEntity toSave = FeedbackTargetEntityMapper.toEntity(feedbackTarget);
            FeedbackTargetEntity saved = repository.save(toSave);
            savedFeedback = FeedbackTargetEntityMapper.toModel(saved);
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
            repository.save(FeedbackTargetEntityMapper.toEntity(target));
        } catch (Exception e) {
            handlePersistenceException("Deleting record", e);
        }
    }

    @Override
    @Transactional
    public PageModel<FeedbackTarget> getAll(final int page, final int size) {
        long totalCount;
        Page<FeedbackTargetEntity> feedbackPage;
        Pageable pageable = PageRequest.of(page, size);
        feedbackPage = repository.findAllFeedbackTargets(pageable);
        totalCount = feedbackPage.getTotalElements();

        return new PageModel<>(Objects.requireNonNull(feedbackPage).map(FeedbackTargetEntityMapper::toModel).getContent(), totalCount);
    }
}
