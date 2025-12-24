package com.jambit.infrastructure.outbound.persistence.mapper;

import com.jambit.domain.feedback.Feedback;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackEntity;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackTargetEntity;

public final class FeedbackEntityMapper {

    private FeedbackEntityMapper() {}

    public static FeedbackEntity toEntity(Feedback model) {
        if (model == null) return null;
        FeedbackEntity e = new FeedbackEntity();
        e.setId(model.getId());
        e.setCreatedOn(model.getCreatedOn());
        e.setUpdatedOn(model.getUpdatedOn());
        e.setDeletedOn(model.getDeletedOn());
        e.setStatus(model.getStatus());
        e.setComment(model.getComment());
        e.setScore(model.getScore());
        e.setTitle(model.getTitle());
        e.setUserId(model.getUserId());
        FeedbackTargetEntity targetEntity = FeedbackTargetEntityMapper.toEntity(model.getFeedbackTarget());
        e.setFeedbackTarget(targetEntity);
        return e;
    }

    public static Feedback toModel(FeedbackEntity entity) {
        if (entity == null) return null;
        Feedback m = new Feedback();
        m.setId(entity.getId());
        m.setCreatedOn(entity.getCreatedOn());
        m.setUpdatedOn(entity.getUpdatedOn());
        m.setDeletedOn(entity.getDeletedOn());
        m.setStatus(entity.getStatus());
        m.setComment(entity.getComment());
        m.setScore(entity.getScore());
        m.setTitle(entity.getTitle());
        m.setUserId(entity.getUserId());
        m.setFeedbackTarget(FeedbackTargetEntityMapper.toModel(entity.getFeedbackTarget()));
        return m;
    }
}

