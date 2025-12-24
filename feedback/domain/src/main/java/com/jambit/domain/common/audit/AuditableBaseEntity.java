package com.jambit.domain.common.audit;

import com.jambit.domain.common.base.AbstractDomainEntity;
import com.jambit.domain.common.base.ModelStatus;
import java.time.LocalDateTime;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:45 PM
 */
public class AuditableBaseEntity extends AbstractDomainEntity {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
    private ModelStatus status = ModelStatus.ACTIVE;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(final LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(final LocalDateTime deletedOn) {
        this.deletedOn = deletedOn;
    }

    public ModelStatus getStatus() {
        return status;
    }

    public void setStatus(final ModelStatus status) {
        this.status = status;
    }
}
