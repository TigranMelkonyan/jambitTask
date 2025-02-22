package com.jambit.infrastructure.integration;

import com.jambit.domain.common.base.ModelStatus;
import com.jambit.domain.common.exception.RecordNotFoundException;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.feedback.TargetType;
import com.jambit.infrastructure.outbound.persistence.FeedbackTargetRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Tigran Melkonyan
 * Date: 2/21/25
 * Time: 5:10 PM
 */
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedbackTargetRepositoryAdapterIT {

    @Autowired
    private FeedbackTargetRepositoryAdapter feedbackTargetRepositoryAdapter;

    private UUID feedbackTargetId;

    @BeforeEach
    void setUp() {
        FeedbackTarget feedbackTarget = new FeedbackTarget();
        feedbackTarget.setTargetType(TargetType.RESTAURANT);
        feedbackTarget.setName("testName");
        feedbackTargetRepositoryAdapter.save(feedbackTarget);
        feedbackTargetId = feedbackTarget.getId();
    }

    @Test
    void shouldGetById() {
        FeedbackTarget found = feedbackTargetRepositoryAdapter.getById(feedbackTargetId);

        assertNotNull(found);
        assertEquals(feedbackTargetId, found.getId());
        assertEquals("testName", found.getName());
    }

    @Test
    void shouldThrowExceptionWhenTargetNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(RecordNotFoundException.class, () ->
                feedbackTargetRepositoryAdapter.getById(nonExistentId)
        );
    }

    @Test
    void shouldCheckIfNameExists() {
        boolean exists = feedbackTargetRepositoryAdapter.existsByName("testName");

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseIfNameDoesNotExist() {
        boolean exists = feedbackTargetRepositoryAdapter.existsByName("Unknown Restaurant");

        assertFalse(exists);
    }

    @Test
    void shouldSaveFeedbackTarget() {
        FeedbackTarget newTarget = new FeedbackTarget();
        newTarget.setName("New Cafe");
        newTarget.setTargetType(TargetType.SHOP);
        newTarget.setStatus(ModelStatus.ACTIVE);

        FeedbackTarget savedTarget = feedbackTargetRepositoryAdapter.save(newTarget);

        assertNotNull(savedTarget.getId());
        assertEquals("New Cafe", savedTarget.getName());
    }

    @Test
    void shouldDeleteFeedbackTarget() {
        feedbackTargetRepositoryAdapter.delete(feedbackTargetId);

        assertThrows(RecordNotFoundException.class, 
                () -> feedbackTargetRepositoryAdapter.getById(feedbackTargetId));
    }

    @Test
    void shouldGetAllFeedbackTargets() {
        PageModel<FeedbackTarget> pageModel = feedbackTargetRepositoryAdapter.getAll(0, 10);

        assertNotNull(pageModel);
        assertFalse(pageModel.getItems().isEmpty());
        assertTrue(pageModel.getTotalCount() > 0);
    }
}

