package com.jambit.domain.feedback;

import com.jambit.domain.common.audit.AuditableBaseEntity;
import com.jambit.domain.common.exception.BusinessRuleViolationException;
import com.jambit.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:52 PM
 */
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"feedbackTarget_id", "userId"})})
public class Feedback extends AuditableBaseEntity {

    @ManyToOne
    @JoinColumn(name = "feedbackTarget_id", nullable = false)
    private FeedbackTarget feedbackTarget;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private short score;

    @Column(nullable = false)
    private UUID userId;

    public static void validateContent(final Feedback feedback) {
        validateScore(feedback.getScore());
        validateComment(feedback.getComment());
        validateTitle(feedback.getTitle());
    }

    private static void validateScore(final short score) {
        if (score < 0 || score > 10) {
            throw new BusinessRuleViolationException("Feedback score must be between 0 to 10", ErrorCode.RULE_EXCEPTION);
        }
    }

    private static void validateComment(final String comment) {
        if (comment == null || comment.length() > 1000) {
            throw new BusinessRuleViolationException("Feedback comment can be max 1000 characters", ErrorCode.RULE_EXCEPTION);
        }
    }

    private static void validateTitle(final String title) {
        if (title == null || title.length() > 1000) {
            throw new BusinessRuleViolationException("Feedback comment can be max 100 characters", ErrorCode.RULE_EXCEPTION);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id != null && id.equals(feedback.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
