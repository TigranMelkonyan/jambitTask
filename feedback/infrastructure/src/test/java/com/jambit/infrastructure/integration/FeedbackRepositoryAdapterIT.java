package com.jambit.infrastructure.integration;

import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.feedback.TargetType;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
import com.jambit.infrastructure.outbound.persistence.FeedbackRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Tigran Melkonyan
 * Date: 2/21/25
 * Time: 4:04 PM
 */
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FeedbackRepositoryAdapterIT {

    @Autowired
    private FeedbackRepositoryAdapter feedbackRepositoryAdapter;

    @Autowired
    private FeedbackTargetRepository feedbackTargetRepository;

    

    private UUID feedbackId;
    private Feedback feedback;
    private UUID feedbackTargetId;
    private short score;
    private String title;
    private FeedbackTarget feedbackTarget;

    @BeforeEach
    void setUp() {
        feedback = new Feedback();
        feedback.setUserId(UUID.randomUUID());
        feedback.setScore((short) 5);
        feedback.setTitle("title");
        feedback.setComment("commenr");

        feedbackTarget = new FeedbackTarget();
        feedbackTarget.setTargetType(TargetType.RESTAURANT);
        feedbackTarget.setName("testName");
        feedbackTargetRepository.save(feedbackTarget);
        feedbackTargetId = feedbackTarget.getId();
        feedback.setFeedbackTarget(feedbackTarget);
        feedbackId = feedbackRepositoryAdapter.save(feedback).getId();
        score = feedback.getScore();
        title = feedback.getTitle();
    }

    @Test
    void findById_ShouldReturnFeedback() {
        Feedback result = feedbackRepositoryAdapter.findById(feedbackId);

        assertNotNull(result);
        assertEquals(feedbackId, result.getId());
        assertEquals(score, result.getScore());
        assertEquals(title, result.getTitle());
    }

    @Test
    void save_ShouldPersistFeedback() {
        Feedback newFeedback = new Feedback();
        newFeedback.setId(UUID.randomUUID());
        newFeedback.setUserId(UUID.randomUUID());
        newFeedback.setFeedbackTarget(feedbackTarget);

        Feedback savedFeedback = feedbackRepositoryAdapter.save(newFeedback);

        assertNotNull(savedFeedback);
        assertNotNull(savedFeedback.getId());
    }

    @Test
    void shouldDeleteById() {
        Feedback newFeedback = new Feedback();
        UUID userId = UUID.randomUUID();
        newFeedback.setId(userId);
        newFeedback.setUserId(UUID.randomUUID());
        newFeedback.setFeedbackTarget(feedbackTarget);

        feedbackRepositoryAdapter.deleteById(feedbackId);

        assertTrue(feedbackRepositoryAdapter.getByUserId(userId).isEmpty());
    }

    @Test
    void shouldReturnAllByFeedbackTargetId() {
        for (int i = 0; i < 5; i++) {
            Feedback feedback = new Feedback();
            feedback.setFeedbackTarget(feedbackTarget);
            feedback.setScore((short) 10);
            feedback.setTitle("title" + i);
            feedback.setComment("commenr");
            feedback.setUserId(UUID.randomUUID());
            feedbackRepositoryAdapter.save(feedback);
        }

        PageModel<Feedback> result = feedbackRepositoryAdapter.getAllByFeedbackTargetId(feedbackTargetId, 0, 5);

        assertTrue(result.getTotalCount() > 0);
    }

    @Test
    void existsByUserIdAndTargetId_ShouldReturnTrue_WhenExists() {
        boolean exists = feedbackRepositoryAdapter.existsByUserIdAndTargetId(feedback.getUserId(), feedbackTargetId);

        assertTrue(exists);
    }
}
