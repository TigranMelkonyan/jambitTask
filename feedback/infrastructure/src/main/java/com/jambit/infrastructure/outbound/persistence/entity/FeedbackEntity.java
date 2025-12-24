package com.jambit.infrastructure.outbound.persistence.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "feedback")
public class FeedbackEntity extends AuditableEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "feedback_target_id", nullable = false)
    private FeedbackTargetEntity feedbackTarget;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "score", nullable = false)
    private short score;

    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private UUID userId;

    public FeedbackTargetEntity getFeedbackTarget() { return feedbackTarget; }
    public void setFeedbackTarget(FeedbackTargetEntity feedbackTarget) { this.feedbackTarget = feedbackTarget; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public short getScore() { return score; }
    public void setScore(short score) { this.score = score; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}

