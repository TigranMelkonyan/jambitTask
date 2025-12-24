package com.jambit.infrastructure.outbound.persistence.entity;

import com.jambit.domain.common.base.ModelStatus;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist
    public void setCreatedOn(AuditableEntity audit) {
        audit.setCreatedOn(LocalDateTime.now());
        audit.setStatus(ModelStatus.ACTIVE);
        setUpdatedOn(audit);
    }

    @PreUpdate
    public void setUpdatedOn(AuditableEntity audit) {
        audit.setUpdatedOn(LocalDateTime.now());
        if (audit.getStatus().equals(ModelStatus.DELETED)) {
            audit.setDeletedOn(LocalDateTime.now());
        } else {
            audit.setDeletedOn(null);
        }
    }
}

