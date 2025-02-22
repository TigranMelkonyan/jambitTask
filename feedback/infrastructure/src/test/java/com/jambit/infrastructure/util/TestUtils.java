package com.jambit.infrastructure.util;

import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.domain.common.base.ModelStatus;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.feedback.TargetType;
import com.jambit.infrastructure.inbound.rest.model.request.CreateFeedbackRequest;
import com.jambit.infrastructure.inbound.rest.model.request.CreateFeedbackTargetRequest;
import com.jambit.infrastructure.inbound.rest.model.response.FeedbackResponse;
import com.jambit.infrastructure.inbound.rest.model.response.FeedbackTargetResponse;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 6:11 PM
 */
public final class TestUtils {

    private TestUtils() {
    }

    public static Feedback createFeedback(final FeedbackTarget feedbackTarget) {
        Feedback feedback = new Feedback();
        feedback.setId(UUID.randomUUID());
        feedback.setTitle("TestFeed");
        feedback.setComment("This is a comment");
        feedback.setUserId(UUID.randomUUID());
        feedback.setFeedbackTarget(feedbackTarget);
        return feedback;
    }

    public static FeedbackTarget createFeedbackTarget() {
        FeedbackTarget feedbackTarget = new FeedbackTarget();
        feedbackTarget.setId(UUID.randomUUID());
        feedbackTarget.setName("Feedback Target");
        feedbackTarget.setStatus(ModelStatus.ACTIVE);
        feedbackTarget.setTargetType(TargetType.RESTAURANT);
        return feedbackTarget;
    }

    public static FeedbackResponse createFeedbackResponse(final UUID feedbackId) {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setId(feedbackId);
        feedbackResponse.setTitle("TestFeed");
        feedbackResponse.setComment("This is a comment");
        return feedbackResponse;
    }

    public static CreateFeedbackRequest createFeedbackRequest(final UUID feedbackId) {
        CreateFeedbackRequest createFeedbackRequest = new CreateFeedbackRequest();
        createFeedbackRequest.setTitle("TestFeed");
        createFeedbackRequest.setComment("This is a comment");
        createFeedbackRequest.setFeedbackTargetId(feedbackId);
        return createFeedbackRequest;
    }

    public static CreateFeedbackCommand createFeedbackCommand(final UUID userId) {
        CreateFeedbackCommand createFeedbackCommand = new CreateFeedbackCommand();
        createFeedbackCommand.setTitle("TestFeed");
        createFeedbackCommand.setComment("This is a comment");
        createFeedbackCommand.setUserId(userId);
        return createFeedbackCommand;
    }

    public static FeedbackTargetResponse createFeedbackTargetResponse(final UUID feedbackTargetId) {
        FeedbackTargetResponse feedbackTargetResponse = new FeedbackTargetResponse();
        feedbackTargetResponse.setId(feedbackTargetId);
        feedbackTargetResponse.setName("Feedback Target");
        feedbackTargetResponse.setTargetType(TargetType.RESTAURANT);
        return feedbackTargetResponse;
    }

    public static CreateFeedbackTargetRequest createFeedbackTargetRequest() {
        CreateFeedbackTargetRequest createFeedbackTargetRequest = new CreateFeedbackTargetRequest();
        createFeedbackTargetRequest.setName("Feedback Target");
        createFeedbackTargetRequest.setTargetType(TargetType.RESTAURANT);
        return createFeedbackTargetRequest;
    }

    public static CreateFeedbackTargetCommand createFeedbackTargetCommand() {
        CreateFeedbackTargetCommand createFeedbackTargetCommand = new CreateFeedbackTargetCommand();
        createFeedbackTargetCommand.setName("Feedback Target");
        createFeedbackTargetCommand.setTargetType(TargetType.RESTAURANT);
        return createFeedbackTargetCommand;
    }

}
