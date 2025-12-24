package com.jambit.infrastructure.outbound.persistence;

import com.jambit.domain.common.exception.RecordNotFoundException;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.repository.feedback.FeedbackRepository;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackEntity;
import com.jambit.infrastructure.outbound.persistence.mapper.FeedbackEntityMapper;
import com.jambit.infrastructure.outbound.persistence.validation.PersistenceErrorProcessor;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        FeedbackEntity entity = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(("Feedback not exists with  id: " + id)));
        return FeedbackEntityMapper.toModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> getByUserId(final UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(FeedbackEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Feedback save(final Feedback feedback) {
        Feedback savedFeedback = null;
        try {
            FeedbackEntity toSave = FeedbackEntityMapper.toEntity(feedback);
            FeedbackEntity saved = repository.save(toSave);
            savedFeedback = FeedbackEntityMapper.toModel(saved);
        } catch (Exception e) {
            handlePersistenceException("Saving record", e);
        }
        return savedFeedback;
    }

    @Override
    @Transactional
    public void deleteById(final UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            handlePersistenceException("Delete record", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserIdAndTargetId(final UUID userId, UUID targetId) {
        return repository.existsByUserIdAndFeedbackTargetId(userId, targetId);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<Feedback> getAllByFeedbackTargetId(final UUID targetId, final int page, final int size) {
        long totalCount;
        Page<FeedbackEntity> feedbackPage;
        Pageable pageable = PageRequest.of(page, size);
        feedbackPage = repository.findAllForFeedbackTarget(targetId, pageable);
        totalCount = feedbackPage.getTotalElements();
        List<Feedback> items = Objects.requireNonNull(feedbackPage).map(FeedbackEntityMapper::toModel).getContent();
        return new PageModel<>(items, totalCount);
    }

}
