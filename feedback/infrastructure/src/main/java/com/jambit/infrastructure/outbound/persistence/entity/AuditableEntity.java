package com.jambit.infrastructure.outbound.persistence.entity;

import com.jambit.domain.common.base.ModelStatus;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class AuditableEntity extends BaseEntity {

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModelStatus status = ModelStatus.ACTIVE;

    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }
    public LocalDateTime getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(LocalDateTime updatedOn) { this.updatedOn = updatedOn; }
    public LocalDateTime getDeletedOn() { return deletedOn; }
    public void setDeletedOn(LocalDateTime deletedOn) { this.deletedOn = deletedOn; }
    public ModelStatus getStatus() { return status; }
    public void setStatus(ModelStatus status) { this.status = status; }
}

