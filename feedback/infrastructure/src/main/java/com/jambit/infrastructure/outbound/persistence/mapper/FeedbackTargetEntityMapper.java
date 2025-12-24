package com.jambit.infrastructure.outbound.persistence.mapper;

import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.outbound.persistence.entity.FeedbackTargetEntity;

public final class FeedbackTargetEntityMapper {

    private FeedbackTargetEntityMapper() {}

    public static FeedbackTargetEntity toEntity(FeedbackTarget model) {
        if (model == null) return null;
        FeedbackTargetEntity e = new FeedbackTargetEntity();
        e.setId(model.getId());
        e.setCreatedOn(model.getCreatedOn());
        e.setUpdatedOn(model.getUpdatedOn());
        e.setDeletedOn(model.getDeletedOn());
        e.setStatus(model.getStatus());
        e.setName(model.getName());
        e.setTargetType(model.getTargetType());
        return e;
    }

    public static FeedbackTarget toModel(FeedbackTargetEntity entity) {
        if (entity == null) return null;
        FeedbackTarget m = new FeedbackTarget();
        m.setId(entity.getId());
        m.setCreatedOn(entity.getCreatedOn());
        m.setUpdatedOn(entity.getUpdatedOn());
        m.setDeletedOn(entity.getDeletedOn());
        m.setStatus(entity.getStatus());
        m.setName(entity.getName());
        m.setTargetType(entity.getTargetType());
        return m;
    }
}
