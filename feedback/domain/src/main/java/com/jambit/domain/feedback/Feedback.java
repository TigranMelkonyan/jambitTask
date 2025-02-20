package com.jambit.domain.feedback;

import com.jambit.domain.common.audit.AuditableBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:52 PM
 */
@Entity
@Getter
@Setter
public class Feedback extends AuditableBaseEntity {

    @ManyToOne
    private FeedbackTarget feedbackTarget;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private short score;

    @Column(nullable = false, unique = true)
    private UUID userId;

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
