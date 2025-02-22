package com.jambit.infrastructure.unit;

import com.jambit.domain.common.exception.RecordNotFoundException;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.feedback.TargetType;
import com.jambit.infrastructure.outbound.persistence.FeedbackJpaRepository;
import com.jambit.infrastructure.outbound.persistence.FeedbackRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tigran Melkonyan
 * Date: 2/21/25
 * Time: 3:24 PM
 */
@ExtendWith(MockitoExtension.class)
class FeedbackRepositoryAdapterTest {

    @Mock
    private FeedbackJpaRepository feedbackJpaRepository;

    @InjectMocks
    private FeedbackRepositoryAdapter feedbackRepositoryAdapter;

    private UUID feedbackId;

    private Feedback feedback;

    @BeforeEach
    void setUp() {
        FeedbackTarget feedbackTarget = new FeedbackTarget();
        feedbackTarget.setName("feedbackTarget");
        feedbackTarget.setTargetType(TargetType.RESTAURANT);
        feedbackId = UUID.randomUUID();
        feedback = new Feedback();
        feedback.setId(feedbackId);
        feedback.setUserId(UUID.randomUUID());
        feedbackTarget.setId(UUID.randomUUID());
        feedback.setFeedbackTarget(feedbackTarget);
    }

    @Test
    void findById_ShouldReturnFeedback_WhenExists() {
        when(feedbackJpaRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));

        Feedback result = feedbackRepositoryAdapter.findById(feedbackId);

        assertNotNull(result);
        assertEquals(feedbackId, result.getId());
    }

    @Test
    void findById_ShouldThrowRecordNotFoundException_WhenNotFound() {
        when(feedbackJpaRepository.findById(feedbackId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> feedbackRepositoryAdapter.findById(feedbackId));
    }

    @Test
    void getByUserId_ShouldReturnListOfFeedback_WhenExists() {
        List<Feedback> feedbackList = List.of(feedback);
        when(feedbackJpaRepository.findByUserId(feedback.getUserId())).thenReturn(feedbackList);

        List<Feedback> result = feedbackRepositoryAdapter.getByUserId(feedback.getUserId());

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void save_ShouldReturnSavedFeedback() {
        when(feedbackJpaRepository.save(feedback)).thenReturn(feedback);

        Feedback result = feedbackRepositoryAdapter.save(feedback);

        assertNotNull(result);
        assertEquals(feedbackId, result.getId());
    }

    @Test
    void deleteByUserId_ShouldDeleteFeedback() {
        doNothing().when(feedbackJpaRepository).deleteById(feedback.getUserId());

        feedbackRepositoryAdapter.deleteById(feedback.getUserId());

        verify(feedbackJpaRepository).deleteById(feedback.getUserId());
    }

    @Test
    void existsByUserIdAndTargetId_ShouldReturnTrue_WhenExists() {
        when(feedbackJpaRepository.existsByUserIdAndFeedbackTargetId(feedback.getUserId(),
                feedback.getFeedbackTarget().getId())).thenReturn(true);

        boolean exists = feedbackRepositoryAdapter.existsByUserIdAndTargetId(feedback.getUserId(),
                feedback.getFeedbackTarget().getId());

        assertTrue(exists);
    }


    @Test
    void existsByUserIdAndTargetId_ShouldReturnFalse_WhenNotExists() {
        when(feedbackJpaRepository.existsByUserIdAndFeedbackTargetId(feedback.getUserId(),
                feedback.getFeedbackTarget().getId())).thenReturn(false);

        boolean result = feedbackRepositoryAdapter.existsByUserIdAndTargetId(feedback.getUserId(), feedback.getFeedbackTarget().getId());

        assertFalse(result);
    }
}

