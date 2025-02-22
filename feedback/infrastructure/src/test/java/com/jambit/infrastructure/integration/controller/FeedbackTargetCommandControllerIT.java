package com.jambit.infrastructure.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.application.service.FeedbackTargetCommandService;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.feedback.FeedbackTargetCommandController;
import com.jambit.infrastructure.inbound.rest.dto.request.CreateFeedbackTargetRequest;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackTargetResponse;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetRequestToCommandMapper;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetResponseMapper;
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
 * Time: 6:05 PM
 */
@WebMvcTest(FeedbackTargetCommandController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
class FeedbackTargetCommandControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackTargetCommandService feedbackTargetCommandService;

    @MockBean
    private FeedbackTargetResponseMapper feedbackResponseMapper;

    @MockBean
    private FeedbackTargetRequestToCommandMapper feedbackRequestToCommandMapper;

    private UUID feedbackTargetId;
    private FeedbackTarget feedbackTarget;
    private FeedbackTargetResponse feedbackTargetResponse;
    private CreateFeedbackTargetRequest createFeedbackTargetRequest;
    private CreateFeedbackTargetCommand createFeedbackTargetCommand;

    @BeforeEach
    void setUp() {
        feedbackTarget = TestUtils.createFeedbackTarget();
        feedbackTargetId = feedbackTarget.getId();

        feedbackTargetResponse = TestUtils.createFeedbackTargetResponse(feedbackTargetId);

        createFeedbackTargetRequest = TestUtils.createFeedbackTargetRequest();

        createFeedbackTargetCommand = TestUtils.createFeedbackTargetCommand();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createFeedbackTarget_ShouldReturnCreatedFeedbackTarget() throws Exception {
        Mockito.when(feedbackRequestToCommandMapper.createFeedbackTargetCommand(createFeedbackTargetRequest)).thenReturn(createFeedbackTargetCommand);
        Mockito.when(feedbackTargetCommandService.create(createFeedbackTargetCommand)).thenReturn(feedbackTarget);
        Mockito.when(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget)).thenReturn(feedbackTargetResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/feedback_targets/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createFeedbackTargetRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackTargetResponse.getId().toString()))
                .andExpect(jsonPath("$.name").value(feedbackTargetResponse.getName()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteFeedbackTarget_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(feedbackTargetCommandService).delete(feedbackTargetId);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/feedback_targets/command/{id}", feedbackTargetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

