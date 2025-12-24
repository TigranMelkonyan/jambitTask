package com.jambit.infrastructure.outbound.persistence.entity;

import com.jambit.domain.feedback.TargetType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "feedback_target")
public class FeedbackTargetEntity extends AuditableEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    public TargetType getTargetType() { return targetType; }
    public void setTargetType(TargetType targetType) { this.targetType = targetType; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

