package com.jambit.infrastructure.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.application.service.FeedbackTargetCommandService;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.feedback.FeedbackTargetCommandController;
import com.jambit.infrastructure.inbound.rest.model.request.CreateFeedbackTargetRequest;
import com.jambit.infrastructure.inbound.rest.model.response.FeedbackTargetResponse;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 4:53 PM
 */
@WebMvcTest(FeedbackTargetCommandController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
class FeedbackTargetCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackTargetCommandService feedbackTargetCommandService;

    @MockBean
    private FeedbackTargetResponseMapper feedbackResponseMapper;

    @MockBean
    private FeedbackTargetRequestToCommandMapper feedbackRequestToCommandMapper;

    private CreateFeedbackTargetRequest createRequest;
    private FeedbackTarget feedbackTarget;
    private FeedbackTargetResponse feedbackTargetResponse;
    private UUID feedbackTargetId;

    @BeforeEach
    void setUp() {
        createRequest = TestUtils.createFeedbackTargetRequest();

        feedbackTarget = TestUtils.createFeedbackTarget();

        feedbackTargetId = feedbackTarget.getId();

        feedbackTargetResponse = TestUtils.createFeedbackTargetResponse(feedbackTargetId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldReturnCreatedFeedbackTarget() throws Exception {
        Mockito.when(feedbackRequestToCommandMapper.createFeedbackTargetCommand(createRequest)).thenReturn(new CreateFeedbackTargetCommand());
        Mockito.when(feedbackTargetCommandService.create(Mockito.any())).thenReturn(feedbackTarget);
        Mockito.when(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget)).thenReturn(feedbackTargetResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/feedback_targets/command")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackTargetResponse.getId().toString()))
                .andExpect(jsonPath("$.name").value(feedbackTargetResponse.getName()));

        Mockito.verify(feedbackTargetCommandService, times(1)).create(Mockito.any());
        Mockito.verify(feedbackResponseMapper, times(1)).feedbackTargetToResponse(feedbackTarget);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldDeleteAndReturnEmptyResponse() throws Exception {
        Mockito.doNothing().when(feedbackTargetCommandService).delete(feedbackTargetId);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/feedback_targets/command/{id}", feedbackTargetId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        Mockito.verify(feedbackTargetCommandService, times(1)).delete(feedbackTargetId);
    }
}

