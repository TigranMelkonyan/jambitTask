package com.jambit.infrastructure.inbound.rest.controller.feedback;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.service.FeedbackCommandService;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.domain.feedback.Feedback;
import com.jambit.infrastructure.inbound.rest.controller.AbstractController;
import com.jambit.infrastructure.inbound.rest.model.request.CreateFeedbackRequest;
import com.jambit.infrastructure.inbound.rest.model.response.FeedbackResponse;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackRequestToCommandMapper;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackResponseMapper;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.infrastructure.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 6:38 PM
 */
@RestController
@RequestMapping("api/feedbacks/command")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackCommandController extends AbstractController {

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

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable final UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        feedbackCommandService.deleteById(id);
        return respondEmpty();
    }
}