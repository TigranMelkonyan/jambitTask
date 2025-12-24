package com.jambit.infrastructure.integration;

import com.jambit.domain.common.base.ModelStatus;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.feedback.TargetType;
import com.jambit.infrastructure.outbound.persistence.FeedbackJpaRepository;
import com.jambit.infrastructure.outbound.persistence.FeedbackRepositoryAdapter;
import com.jambit.infrastructure.outbound.persistence.FeedbackTargetJpaRepository;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackEntity;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackTargetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Tigran Melkonyan
 * Date: 2/21/25
 * Time: 3:31 PM
 */
@DataJpaTest
@EntityScan(basePackages = {"com.jambit.infrastructure"})
@Import(FeedbackRepositoryAdapter.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FeedbackJpaRepositoryTest {

    @Autowired
    private FeedbackJpaRepository feedbackJpaRepository;

    @Autowired
    private FeedbackTargetJpaRepository feedbackTargetJpaRepository;
    
    @Autowired
    private FeedbackRepositoryAdapter feedbackRepositoryAdapter;

    private UUID feedbackId;
    private FeedbackTargetEntity feedbackTargetEntity;
    private UUID userId;
    private UUID targetId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        feedbackTargetEntity = new FeedbackTargetEntity();
        feedbackTargetEntity.setName("test");
        feedbackTargetEntity.setTargetType(TargetType.RESTAURANT);
        feedbackTargetJpaRepository.save(feedbackTargetEntity);
        targetId = feedbackTargetEntity.getId();

        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setUserId(userId);
        feedback.setFeedbackTarget(feedbackTargetEntity);
        feedback.setTitle("title");
        feedback.setScore((short) 1);
        feedback.setStatus(ModelStatus.ACTIVE);
        feedbackJpaRepository.save(feedback);
        feedbackId = feedback.getId();
    }

    @Test
    void shouldFindFeedbackByUserId() {
        List<FeedbackEntity> feedbacks = feedbackJpaRepository.findByUserId(userId);

        assertNotNull(feedbacks);
        assertFalse(feedbacks.isEmpty());
        assertEquals(userId, feedbacks.get(0).getUserId());
    }

    @Test
    void shouldReturnFeedbackById() {
        Optional<FeedbackEntity> result = feedbackJpaRepository.findById(feedbackId);

        assertTrue(result.isPresent());
        assertEquals(feedbackId, result.get().getId());
    }

    @Test
    void shouldReturnFalseIfFeedbackDoesNotExist() {
        UUID nonExistentFeedbackId = UUID.randomUUID();
        Optional<FeedbackEntity> result = feedbackJpaRepository.findById(nonExistentFeedbackId);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldDeleteFeedbackById() {
        feedbackJpaRepository.deleteById(feedbackId);

        Optional<FeedbackEntity> feedbacks = feedbackJpaRepository.findById(feedbackId);
        assertTrue(feedbacks.isEmpty());
    }

    @Test
    void shouldReturnTrueIfExistsByUserIdAndTargetId() {
        boolean exists = feedbackJpaRepository.existsByUserIdAndFeedbackTargetId(userId, targetId);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseIfNotExistsByUserIdAndTargetId() {
        UUID nonExistentTargetId = UUID.randomUUID();
        boolean exists = feedbackJpaRepository.existsByUserIdAndFeedbackTargetId(userId, nonExistentTargetId);

        assertFalse(exists);
    }

    @Test
    void shouldReturnFeedbacksForTargetId() {
        for (int i = 0; i < 5; i++) {
            FeedbackTarget domainTarget = new FeedbackTarget();
            domainTarget.setId(feedbackTargetEntity.getId());
            domainTarget.setName(feedbackTargetEntity.getName());
            domainTarget.setTargetType(feedbackTargetEntity.getTargetType());
            Feedback feedback = new Feedback();
            feedback.setScore((short) 8);
            feedback.setTitle("title");
            feedback.setUserId(UUID.randomUUID());
            feedback.setStatus(ModelStatus.ACTIVE);
            feedback.setFeedbackTarget(domainTarget);
            feedbackRepositoryAdapter.save(feedback);
        }

        PageRequest pageable = PageRequest.of(0, 10);
        Page<FeedbackEntity> page = feedbackJpaRepository.findAllForFeedbackTarget(targetId, pageable);

        assertFalse(page.isEmpty());
        assertTrue(page.getTotalElements() > 0);
    }
}
