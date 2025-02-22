package com.jambit.infrastructure.integration.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 5:55 PM
 */
@WebMvcTest(FeedbackCommandController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
public class FeedbackCommandControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackCommandService feedbackCommandService;

    @MockBean
    private FeedbackResponseMapper feedbackResponseMapper;

    @MockBean
    private FeedbackRequestToCommandMapper feedbackRequestToCommandMapper;

    private UUID userId;
    private Feedback feedback;
    private FeedbackResponse feedbackResponse;
    private CreateFeedbackRequest createFeedbackRequest;
    private CreateFeedbackCommand createFeedbackCommand;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        FeedbackTarget feedbackTarget = TestUtils.createFeedbackTarget();
        UUID feedbackId = feedbackTarget.getId();

        feedback = TestUtils.createFeedback(feedbackTarget);

        feedbackResponse = TestUtils.createFeedbackResponse(feedbackId);

        createFeedbackRequest = TestUtils.createFeedbackRequest(feedbackId);

        createFeedbackCommand = TestUtils.createFeedbackCommand(userId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void createFeedback_ShouldReturnCreatedFeedback() throws Exception {
        Mockito.when(feedbackRequestToCommandMapper.createFeedbackCommand(createFeedbackRequest)).thenReturn(createFeedbackCommand);
        Mockito.when(feedbackCommandService.create(createFeedbackCommand)).thenReturn(feedback);
        Mockito.when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/feedbacks/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createFeedbackRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$.title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$.comment").value(feedbackResponse.getComment()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteFeedback_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(feedbackCommandService).deleteByUser(userId);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/feedbacks/command/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

