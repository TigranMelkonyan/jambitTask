package com.jambit.infrastructure.outbound.persistence;

import com.jambit.infrastructure.outbound.persistence.entity.FeedbackTargetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 2:33 PM
 */
public interface FeedbackTargetJpaRepository extends JpaRepository<FeedbackTargetEntity, UUID> {

    @Query("select f from FeedbackTargetEntity f where f.status = 'ACTIVE'")
    Page<FeedbackTargetEntity> findAllFeedbackTargets(Pageable pageable);

    @Query("select f from FeedbackTargetEntity f where f.id=:id and f.status = 'ACTIVE'")
    Optional<FeedbackTargetEntity> findByIdAndAuditStatus(@Param("id") UUID id);
    
    boolean existsByName(String name);
}
