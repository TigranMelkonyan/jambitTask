package com.jambit.infrastructure.unit.controller;

import com.jambit.application.query.GetAllFeedbacksByTargetQuery;
import com.jambit.application.service.FeedbackQueryService;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.Feedback;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.feedback.FeedbackQueryController;
import com.jambit.infrastructure.inbound.rest.model.response.FeedbackResponse;
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

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 4:32 PM
 */
@WebMvcTest(FeedbackQueryController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
class FeedbackQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackQueryService feedbackQueryService;

    @MockBean
    private FeedbackResponseMapper feedbackResponseMapper;

    private UUID feedbackId;
    private UUID userId;
    private UUID feedbackTargetId;
    private Feedback feedback;
    private FeedbackResponse feedbackResponse;

    @BeforeEach
    void setUp() {
        FeedbackTarget feedbackTarget = TestUtils.createFeedbackTarget();

        feedbackId = feedbackTarget.getId();

        feedback = TestUtils.createFeedback(feedbackTarget);
        feedbackTargetId = feedbackTarget.getId();
        userId = feedback.getUserId();
                
        feedbackResponse = new FeedbackResponse();
        feedbackResponse.setId(feedbackId);
        feedbackResponse.setTitle("Test Feedback Response");
        feedbackResponse.setFeedbackTargetId(feedbackTargetId);
        feedbackResponse.setUserId(userId);
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetFeedback() throws Exception {
        Mockito.when(feedbackQueryService.findById(feedbackId)).thenReturn(feedback);
        Mockito.when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/feedbacks/query/{id}", feedbackId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$.title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$.userId").value(feedbackResponse.getUserId().toString()))
                .andExpect(jsonPath("$.feedbackTargetId").value(feedbackResponse.getFeedbackTargetId().toString()));

        Mockito.verify(feedbackQueryService, times(1)).findById(feedbackId);
        Mockito.verify(feedbackResponseMapper, times(1)).feedbackToResponse(feedback);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testGetFeedbackByUser() throws Exception {
        Mockito.when(feedbackQueryService.findByUserId(userId)).thenReturn(List.of(feedback));
        Mockito.when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/feedbacks/query/{id}/by_user", userId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$[0].title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$[0].userId").value(feedbackResponse.getUserId().toString()))
                .andExpect(jsonPath("$[0].feedbackTargetId").value(feedbackResponse.getFeedbackTargetId().toString()));

        Mockito.verify(feedbackQueryService, times(1)).findByUserId(userId);
        Mockito.verify(feedbackResponseMapper, times(1)).feedbackToResponse(feedback);
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetFeedbacksByTargetId() throws Exception {
        PageModel<Feedback> feedbackPage = new PageModel<>(List.of(feedback), 1);

        Mockito.when(feedbackQueryService.getAll(Mockito.any(GetAllFeedbacksByTargetQuery.class))).thenReturn(feedbackPage);
        Mockito.when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/feedbacks/query/{target_id}/pages", feedbackTargetId)
                .param("pageNumber", "0")
                .param("pageSize", "1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$.items[0].title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$.totalCount").value(1));

        Mockito.verify(feedbackQueryService, VerificationModeFactory.times(1)).getAll(Mockito.any(GetAllFeedbacksByTargetQuery.class));
        Mockito.verify(feedbackResponseMapper, VerificationModeFactory.times(1)).feedbackToResponse(feedback);
    }
}

