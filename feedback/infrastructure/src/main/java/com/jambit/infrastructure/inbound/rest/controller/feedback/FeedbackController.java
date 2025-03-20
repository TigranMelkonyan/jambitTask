package com.jambit.infrastructure.inbound.rest.controller.feedback;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.query.GetAllFeedbacksByTargetQuery;
import com.jambit.application.service.FeedbackCommandService;
import com.jambit.application.service.FeedbackQueryService;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;
import com.jambit.infrastructure.inbound.rest.controller.AbstractResponseController;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackRequestToCommandMapper;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackResponseMapper;
import com.jambit.infrastructure.inbound.rest.model.request.CreateFeedbackRequest;
import com.jambit.infrastructure.inbound.rest.model.response.FeedbackResponse;
import com.jambit.infrastructure.inbound.rest.model.response.PageResponse;
import com.jambit.infrastructure.util.SecurityContextUtil;
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
@RequestMapping("api/feedbacks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackController extends AbstractResponseController {

    private final FeedbackQueryService feedbackQueryService;
    private final FeedbackCommandService feedbackCommandService;
    private final FeedbackResponseMapper feedbackResponseMapper;
    private final FeedbackRequestToCommandMapper feedbackRequestToCommandMapper;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<FeedbackResponse> create(@RequestBody final CreateFeedbackRequest request) {
        ModelValidator.validate(request);
        CreateFeedbackCommand command = feedbackRequestToCommandMapper.createFeedbackCommand(request);
        command.setUserId(SecurityContextUtil.currentUserId());
        Feedback feedback = feedbackCommandService.create(command);
        return respondOK(feedbackResponseMapper.feedbackToResponse(feedback));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<FeedbackResponse> getFeedback(@PathVariable("id") UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        Feedback feedback = feedbackQueryService.findById(id);
        return respondOK(feedbackResponseMapper.feedbackToResponse(feedback));
    }

    @GetMapping("{id}/by_user")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<FeedbackResponse>> getFeedbackByUser(@PathVariable("id") UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        List<Feedback> feedbacks = feedbackQueryService.findByUserId(id);
        List<FeedbackResponse> responses = feedbacks.stream()
                .map(feedbackResponseMapper::feedbackToResponse)
                .collect(Collectors.toList());
        return respondOK(responses);
    }

    @GetMapping("{target_id}/pages")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<PageResponse<FeedbackResponse>> getFeedbacks(
            final int pageNumber,
            final int pageSize,
            @PathVariable("target_id") final UUID targetId) {
        NullCheckUtils.checkNullConstraints(List.of("pageNumber", "pageSize"), pageNumber, pageSize);
        PageModel<Feedback> result = feedbackQueryService
                .getAll(new GetAllFeedbacksByTargetQuery(targetId, pageNumber, pageSize));
        PageResponse<FeedbackResponse> response = new PageResponse<>(result
                .getItems()
                .stream()
                .map(feedbackResponseMapper::feedbackToResponse)
                .collect(Collectors.toList()),
                result.getTotalCount());
        return respondOK(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable final UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        feedbackCommandService.deleteById(id);
        return respondEmpty();
    }
}