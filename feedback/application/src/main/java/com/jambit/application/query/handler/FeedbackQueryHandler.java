package com.jambit.application.query.handler;

import com.jambit.application.query.GetAllFeedbacksByTargetQuery;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.repository.feedback.FeedbackRepository;
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
public class FeedbackQueryHandler {

    private final FeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    public List<Feedback> findByUserId(final UUID id) {
        log.info("Retrieving feedback with user id - {} ", id);
        List<Feedback> feedback = feedbackRepository.getByUserId(id);
        log.info("Successfully retrieved feedback with user id - {}, result - {}", id, feedback);
        return feedback;
    }

    @Transactional(readOnly = true)
    public Feedback findById(final UUID id) {
        log.info("Retrieving feedback with  id - {} ", id);
        Feedback feedback = feedbackRepository.findById(id);
        log.info("Successfully retrieved feedback with  id - {}, result - {}", id, feedback);
        return feedback;
    }

    @Transactional(readOnly = true)
    public PageModel<Feedback> handle(final GetAllFeedbacksByTargetQuery query) {
        log.info("Retrieving feedbacks with target id - {} ", query.getTargetId());
        PageModel<Feedback> pages = feedbackRepository
                .getAllByFeedbackTargetId(query.getTargetId(), query.getPage(), query.getSize());
        log.info("Successfully retrieved feedbacks with target id - {}, result - {}", query.getTargetId(), pages);
        return pages;
    }

}
