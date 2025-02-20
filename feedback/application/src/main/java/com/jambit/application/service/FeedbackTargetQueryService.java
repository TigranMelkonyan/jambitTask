package com.jambit.application.service;

import com.jambit.application.query.GetAllFeedbackTargetsQuery;
import com.jambit.application.query.handler.FeedbackTargetQueryHandler;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 1:22 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackTargetQueryService {

    private final FeedbackTargetQueryHandler feedbackTargetQueryHandler;
    private final ModelValidator modelValidator;

    public FeedbackTarget findById(final UUID id) {
        log.info("Processing retrieving feedback target with id - {} ", id);
        Assert.notNull(id, "Id must not be null");
        return feedbackTargetQueryHandler.findById(id);
    }

    public PageModel<FeedbackTarget> getALl(final GetAllFeedbackTargetsQuery query) {
        log.info("Processing retrieving feedback targets with page - {} size - {}", query.getPage(), query.getSize());
        modelValidator.validate(query);
        return feedbackTargetQueryHandler.handle(query);
    }

}
