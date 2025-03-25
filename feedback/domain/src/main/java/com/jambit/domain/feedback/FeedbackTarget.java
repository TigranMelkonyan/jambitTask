package com.jambit.domain.feedback;

import com.jambit.domain.common.audit.AuditableBaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:14 AM
 */
@Entity
public class FeedbackTarget extends AuditableBaseEntity {

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private String name;

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(final TargetType targetType) {
        this.targetType = targetType;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackTarget target = (FeedbackTarget) o;
        return id != null && id.equals(target.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
