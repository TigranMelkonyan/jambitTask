package com.jambit.infrastructure.inbound.rest.controller.feedback;

import com.jambit.application.query.GetAllFeedbackTargetsQuery;
import com.jambit.application.service.FeedbackTargetQueryService;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.AbstractController;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackTargetResponse;
import com.jambit.infrastructure.inbound.rest.dto.response.PageResponse;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetResponseMapper;
import com.jambit.application.util.NullCheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 6:38 PM
 */
@RestController
@RequestMapping("api/feedback_targets/query")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackTargetQueryController extends AbstractController {

    private final FeedbackTargetResponseMapper feedbackResponseMapper;
    private final FeedbackTargetQueryService feedbackTargetQueryService;

    @GetMapping("{id}")
    public ResponseEntity<FeedbackTargetResponse> getFeedbackTarget(@PathVariable("id") UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        FeedbackTarget feedbackTarget = feedbackTargetQueryService.findById(id);
        return respondOK(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget));
    }

    @GetMapping("pages")
    public ResponseEntity<PageResponse<FeedbackTargetResponse>> getFeedbackTargets(final int pageNumber, final int pageSize) {
        NullCheckUtils.checkNullConstraints(List.of("pageNumber", "pageSize"), pageNumber, pageSize);
        PageModel<FeedbackTarget> result = feedbackTargetQueryService
                .getAll(new GetAllFeedbackTargetsQuery(pageNumber, pageSize));
        PageResponse<FeedbackTargetResponse> response = new PageResponse<>(result
                .getItems()
                .stream()
                .map(feedbackResponseMapper::feedbackTargetToResponse)
                .collect(Collectors.toList()),
                result.getTotalCount());
        return respondOK(response);
    }
}