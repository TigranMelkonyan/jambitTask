package com.jambit.infrastructure.outbound.persistence;

import com.jambit.domain.feedback.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 2:33 PM
 */
public interface FeedbackJpaRepository extends JpaRepository<Feedback, UUID> {

    List<Feedback> findByUserId(UUID userId);
    
    boolean existsByUserIdAndFeedbackTargetId(UUID userId, UUID targetId);

    @Query("select f from Feedback f where f.feedbackTarget.id =:targetId and f.status = 'ACTIVE'")
    Page<Feedback> findAllForFeedbackTarget(@Param("targetId") UUID targetId, Pageable pageable);
    
}
