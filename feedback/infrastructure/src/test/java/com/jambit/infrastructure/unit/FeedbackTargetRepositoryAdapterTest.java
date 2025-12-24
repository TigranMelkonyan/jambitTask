package com.jambit.infrastructure.unit;

import com.jambit.domain.common.base.ModelStatus;
import com.jambit.domain.common.exception.RecordNotFoundException;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.outbound.persistence.FeedbackTargetJpaRepository;
import com.jambit.infrastructure.outbound.persistence.FeedbackTargetRepositoryAdapter;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackTargetEntity;
import com.jambit.infrastructure.outbound.persistence.mapper.FeedbackTargetEntityMapper;
import com.jambit.infrastructure.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tigran Melkonyan
 * Date: 2/21/25
 * Time: 2:40 PM
 */
@ExtendWith(MockitoExtension.class)
class FeedbackTargetRepositoryAdapterTest {

    @Mock
    private FeedbackTargetJpaRepository repository;

    @InjectMocks
    private FeedbackTargetRepositoryAdapter adapter;

    private UUID feedbackTargetId;
    private FeedbackTarget feedbackTarget;

    @BeforeEach
    void setUp() {
        feedbackTarget = TestUtils.createFeedbackTarget();
        feedbackTargetId = feedbackTarget.getId();
    }

    @Test
    void getById_ShouldReturnFeedbackTarget_WhenExists() {
        FeedbackTarget mockTarget = TestUtils.createFeedbackTarget();
        UUID targetId = mockTarget.getId();
        FeedbackTargetEntity entity = FeedbackTargetEntityMapper.toEntity(mockTarget);

        when(repository.findByIdAndAuditStatus(targetId))
                .thenReturn(Optional.of(entity));

        FeedbackTarget result = adapter.getById(targetId);

        assertNotNull(result);
        assertEquals(targetId, result.getId());
        verify(repository).findByIdAndAuditStatus(targetId);
    }

    @Test
    void getById_ShouldThrowRecordNotFoundException_WhenNotExists() {
        when(repository.findByIdAndAuditStatus(feedbackTargetId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> adapter.getById(feedbackTargetId));

        verify(repository).findByIdAndAuditStatus(feedbackTargetId);
    }


    @Test
    void existsByName_ShouldReturnTrue_WhenExists() {
        String name = "TestName";
        when(repository.existsByName(name)).thenReturn(true);

        boolean result = adapter.existsByName(name);

        assertTrue(result);
        verify(repository).existsByName(name);
    }

    @Test
    void existsByName_ShouldReturnFalse_WhenNotExists() {
        String name = "NonexistentName";
        when(repository.existsByName(name)).thenReturn(false);

        boolean result = adapter.existsByName(name);

        assertFalse(result);
        verify(repository).existsByName(name);
    }

    @Test
    void save_ShouldReturnSavedFeedbackTarget() {
        when(repository.save(any(FeedbackTargetEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FeedbackTarget result = adapter.save(feedbackTarget);

        assertNotNull(result);
        assertEquals(feedbackTargetId, result.getId());
        verify(repository).save(any(FeedbackTargetEntity.class));
    }

    @Test
    void delete_ShouldMarkAsDeleted_WhenFeedbackTargetExists() {
        when(repository.findByIdAndAuditStatus(feedbackTargetId))
                .thenReturn(Optional.of(FeedbackTargetEntityMapper.toEntity(feedbackTarget)));

        when(repository.save(any(FeedbackTargetEntity.class))).thenReturn(FeedbackTargetEntityMapper.toEntity(feedbackTarget));

        adapter.delete(feedbackTargetId);

        verify(repository).findByIdAndAuditStatus(feedbackTargetId);
        org.mockito.ArgumentCaptor<FeedbackTargetEntity> captor = org.mockito.ArgumentCaptor.forClass(FeedbackTargetEntity.class);
        verify(repository).save(captor.capture());
        assertEquals(ModelStatus.DELETED, captor.getValue().getStatus());
    }

    @Test
    void getAll_ShouldReturnPageModel() {
        int page = 0, size = 10;
        List<FeedbackTargetEntity> feedbackTargets = List.of(FeedbackTargetEntityMapper.toEntity(feedbackTarget));
        Page<FeedbackTargetEntity> mockPage = new PageImpl<>(feedbackTargets);
        when(repository.findAllFeedbackTargets(PageRequest.of(page, size))).thenReturn(mockPage);

        PageModel<FeedbackTarget> result = adapter.getAll(page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalCount());
        assertEquals(1, result.getItems().size());
        verify(repository).findAllFeedbackTargets(PageRequest.of(page, size));
    }
}
