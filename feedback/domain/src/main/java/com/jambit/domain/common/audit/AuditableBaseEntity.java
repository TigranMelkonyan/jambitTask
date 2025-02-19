package com.jambit.domain.common.audit;

import com.jambit.domain.common.base.AbstractDomainEntity;
import com.jambit.domain.common.base.ModelStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:45 PM
 */
@EntityListeners(AuditListener.class)
@Getter
@Setter
@MappedSuperclass
public class AuditableBaseEntity extends AbstractDomainEntity {

    @CreatedDate
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @LastModifiedDate
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModelStatus status = ModelStatus.ACTIVE;

}
