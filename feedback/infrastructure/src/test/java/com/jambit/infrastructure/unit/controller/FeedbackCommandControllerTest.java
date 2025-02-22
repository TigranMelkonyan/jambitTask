package com.jambit.infrastructure.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jambit.application.command.CreateFeedbackCommand;
import com.jambit.application.service.FeedbackCommandService;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.feedback.FeedbackCommandController;
import com.jambit.infrastructure.inbound.rest.dto.request.CreateFeedbackRequest;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackResponse;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackRequestToCommandMapper;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackResponseMapper;
import com.jambit.infrastructure.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 1:52 PM
 */
@WebMvcTest(FeedbackCommandController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
class FeedbackCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackCommandService feedbackCommandService;

    @MockBean
    private FeedbackResponseMapper feedbackResponseMapper;

    @MockBean
    private FeedbackRequestToCommandMapper feedbackRequestToCommandMapper;

    private CreateFeedbackRequest request;
    private CreateFeedbackCommand command;
    private Feedback feedback;
    private FeedbackResponse feedbackResponse;
    private UUID userId;

    @BeforeEach
    void setUp() {
        FeedbackTarget feedbackTarget = TestUtils.createFeedbackTarget();
        UUID feedbackTargetId = feedbackTarget.getId();

        feedback = TestUtils.createFeedback(feedbackTarget);
        userId = feedback.getUserId();
        UUID feedbackId = feedback.getId();

        request = TestUtils.createFeedbackRequest(feedbackTargetId);

        command = TestUtils.createFeedbackCommand(userId);

        feedbackResponse = TestUtils.createFeedbackResponse(feedbackId);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void shouldCreateFeedback() throws Exception {
        when(feedbackRequestToCommandMapper.createFeedbackCommand(request)).thenReturn(command);
        when(feedbackCommandService.create(any(CreateFeedbackCommand.class))).thenReturn(feedback);
        when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/feedbacks/command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$.title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$.comment").value(feedbackResponse.getComment()))
                .andExpect(jsonPath("$.userId").value(feedbackResponse.getUserId()))
                .andExpect(jsonPath("$.feedbackTargetId").value(feedbackResponse.getFeedbackTargetId()));

        Mockito.verify(feedbackCommandService, times(1)).create(any(CreateFeedbackCommand.class));
        Mockito.verify(feedbackResponseMapper, VerificationModeFactory.times(1)).feedbackToResponse(feedback);
        Mockito.verify(feedbackRequestToCommandMapper, VerificationModeFactory.times(1)).createFeedbackCommand(request);

    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteFeedbackByUserId() throws Exception {
        mockMvc.perform(delete("/api/feedbacks/command/{userId}", userId))
                .andExpect(status().isNoContent());

        Mockito.verify(feedbackCommandService).deleteById(userId);
    }
}
