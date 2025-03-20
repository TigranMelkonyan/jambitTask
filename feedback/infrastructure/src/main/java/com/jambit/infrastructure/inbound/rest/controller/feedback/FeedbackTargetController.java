package com.jambit.infrastructure.inbound.rest.controller.feedback;

import com.jambit.application.query.GetAllFeedbackTargetsQuery;
import com.jambit.application.service.FeedbackTargetCommandService;
import com.jambit.application.service.FeedbackTargetQueryService;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.AbstractResponseController;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetRequestToCommandMapper;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetResponseMapper;
import com.jambit.infrastructure.inbound.rest.model.request.CreateFeedbackTargetRequest;
import com.jambit.infrastructure.inbound.rest.model.response.FeedbackTargetResponse;
import com.jambit.infrastructure.inbound.rest.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("api/feedback_targets")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackTargetController extends AbstractResponseController {

    private final FeedbackTargetResponseMapper feedbackResponseMapper;
    private final FeedbackTargetCommandService feedbackTargetCommandService;
    private final FeedbackTargetQueryService feedbackTargetQueryService;
    private final FeedbackTargetRequestToCommandMapper feedbackRequestToCommandMapper;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<FeedbackTargetResponse> create(@RequestBody final CreateFeedbackTargetRequest request) {
        ModelValidator.validate(request);
        FeedbackTarget feedbackTarget = feedbackTargetCommandService.create(feedbackRequestToCommandMapper.createFeedbackTargetCommand(request));
        return respondOK(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<FeedbackTargetResponse> getFeedbackTarget(@PathVariable("id") UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        FeedbackTarget feedbackTarget = feedbackTargetQueryService.findById(id);
        return respondOK(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget));
    }

    @GetMapping("pages")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<FeedbackTargetResponse> delete(@PathVariable final UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        feedbackTargetCommandService.delete(id);
        return respondEmpty();
    }

}