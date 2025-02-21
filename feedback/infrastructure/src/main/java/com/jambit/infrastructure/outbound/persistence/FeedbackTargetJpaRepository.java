package com.jambit.infrastructure.outbound.persistence;

import com.jambit.domain.feedback.FeedbackTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 2:33 PM
 */
public interface FeedbackTargetJpaRepository extends JpaRepository<FeedbackTarget, UUID> {

    @Query("select f from FeedbackTarget f where f.status = 'ACTIVE'")
    Page<FeedbackTarget> findAllFeedbackTargets(Pageable pageable);

    boolean existsByName(String name);
}
