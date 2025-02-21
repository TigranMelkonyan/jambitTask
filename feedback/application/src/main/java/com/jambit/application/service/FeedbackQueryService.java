package com.jambit.application.service;

import com.jambit.application.query.GetAllFeedbacksByTargetQuery;
import com.jambit.application.query.handler.FeedbackQueryHandler;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 1:22 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackQueryService {

    private final FeedbackQueryHandler feedbackQueryHandler;

    public List<Feedback> findByUserId(final UUID id) {
        log.info("Processing retrieving feedback with user id - {} ", id);
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        return feedbackQueryHandler.findByUserId(id);
    }

    public Feedback findById(final UUID id) {
        log.info("Processing retrieving feedback with id - {} ", id);
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        return feedbackQueryHandler.findById(id);
    }

    public PageModel<Feedback> getAll(final GetAllFeedbacksByTargetQuery query) {
        log.info("Processing retrieving feedbacks with target id - {} ", query.getTargetId());
        ModelValidator.validate(query);
        return feedbackQueryHandler.handle(query);
    }

}
