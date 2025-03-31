package com.jambit.domain.feedback;

import com.jambit.domain.common.audit.AuditableBaseEntity;
import com.jambit.domain.common.exception.BusinessRuleViolationException;
import com.jambit.domain.common.exception.ErrorCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:52 PM
 */
@Entity
public class Feedback extends AuditableBaseEntity {

    @ManyToOne
    private FeedbackTarget feedbackTarget;

    private String title;
    private String comment;
    private short score;
    private UUID userId;

    //Defines business restrictions
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

    public FeedbackTarget getFeedbackTarget() {
        return feedbackTarget;
    }

    public void setFeedbackTarget(final FeedbackTarget feedbackTarget) {
        this.feedbackTarget = feedbackTarget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public short getScore() {
        return score;
    }

    public void setScore(final short score) {
        this.score = score;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
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
