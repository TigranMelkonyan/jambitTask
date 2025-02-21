package com.jambit.infrastructure.inbound.rest.controller.feedback;

import com.jambit.application.service.FeedbackTargetCommandService;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.AbstractController;
import com.jambit.infrastructure.inbound.rest.dto.request.CreateFeedbackTargetRequest;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackTargetResponse;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetRequestToCommandMapper;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetResponseMapper;
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
@RequestMapping("api/feedback_targets/command")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackTargetCommandController extends AbstractController {

    private final FeedbackTargetCommandService feedbackTargetCommandService;
    private final FeedbackTargetResponseMapper feedbackResponseMapper;
    private final FeedbackTargetRequestToCommandMapper feedbackRequestToCommandMapper;


    @PostMapping
    public ResponseEntity<FeedbackTargetResponse> create(@RequestBody final CreateFeedbackTargetRequest request) {
        ModelValidator.validate(request);
        FeedbackTarget feedbackTarget = feedbackTargetCommandService.create(feedbackRequestToCommandMapper.createFeedbackTargetCommand(request));
        return respondOK(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<FeedbackTargetResponse> delete(@PathVariable final UUID id) {
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        feedbackTargetCommandService.delete(id);
        return respondEmpty();
    }
}