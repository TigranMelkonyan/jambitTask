package com.jambit.infrastructure.integration;

import com.jambit.domain.common.base.ModelStatus;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.feedback.TargetType;
import com.jambit.infrastructure.outbound.persistence.FeedbackTargetJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Tigran Melkonyan
 * Date: 2/21/25
 * Time: 2:52 PM
 */
@DataJpaTest
@EntityScan(basePackages = {"com.jambit.domain"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedbackTargetJpaRepositoryTest {

    @Autowired
    private FeedbackTargetJpaRepository repository;
    
    @BeforeEach
    void setUp() {
        FeedbackTarget feedbackTarget = new FeedbackTarget();
        feedbackTarget.setStatus(ModelStatus.ACTIVE);
        feedbackTarget.setName("TestTarget");
        feedbackTarget.setTargetType(TargetType.RESTAURANT);
        repository.save(feedbackTarget);
    }

    @Test
    void existsByName_ShouldReturnTrue_WhenNameExists() {
        assertTrue(repository.existsByName("TestTarget"));
    }

    @Test
    void existsByName_ShouldReturnFalse_WhenNameNotExists() {
        assertFalse(repository.existsByName("Nonexistent Name"));
    }

    @Test
    void shouldReturnAllFeedbackTargets() {
        for (int i = 0; i < 5; i++) {
            FeedbackTarget feedbackTarget = new FeedbackTarget();
            feedbackTarget.setStatus(ModelStatus.ACTIVE);
            feedbackTarget.setTargetType(TargetType.RESTAURANT);
            feedbackTarget.setName("target" + "_" + i);
            repository.save(feedbackTarget);
        }

        Page<FeedbackTarget> page = repository.findAllFeedbackTargets(Pageable.ofSize(5));

        assertTrue(page.getTotalElements() > 0);

    }
}

