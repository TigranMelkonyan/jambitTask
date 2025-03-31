package com.jambit.application.query.handler;

import com.jambit.application.query.GetAllFeedbackTargetsQuery;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:24 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackTargetQueryHandler {

    private final FeedbackTargetRepository feedbackRepository;

    @Transactional(readOnly = true)
    public FeedbackTarget findById(final UUID id) {
        log.info("Retrieving feedback target with id - {} ", id);
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        FeedbackTarget feedbackTarget = feedbackRepository.getById(id);
        log.info("Successfully retrieved feedback target with id - {}, result - {}", id, feedbackTarget);
        return feedbackTarget;
    }

    @Transactional(readOnly = true)
    public PageModel<FeedbackTarget> getAll(final GetAllFeedbackTargetsQuery query) {
        log.info("Retrieving feedback targets with page - {} size - {}", query.getPage(), query.getSize());
        ModelValidator.validate(query);
        PageModel<FeedbackTarget> pages = feedbackRepository
                .getAll(query.getPage(), query.getSize());
        log.info("Successfully retrieved feedback targets result - {}", pages);
        return pages;
    }

}
