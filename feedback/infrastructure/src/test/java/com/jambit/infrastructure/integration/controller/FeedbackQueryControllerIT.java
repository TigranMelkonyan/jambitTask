package com.jambit.infrastructure.integration.controller;

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
import org.mockito.Mock;
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

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 5:44 PM
 */
@WebMvcTest(FeedbackQueryController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
public class FeedbackQueryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackQueryService feedbackQueryService;

    @MockBean
    private FeedbackResponseMapper feedbackResponseMapper;

    private UUID feedbackId;
    private Feedback feedback;
    private FeedbackResponse feedbackResponse;
    private PageModel<Feedback> feedbackPageModel;
    
    @Mock
    private FeedbackTarget feedbackTarget;

    @BeforeEach
    void setUp() {
        feedbackId = UUID.randomUUID();

        feedback = TestUtils.createFeedback(feedbackTarget);

        feedbackResponse =TestUtils.createFeedbackResponse(feedbackId);

        feedbackPageModel = new PageModel<>(List.of(feedback), 1);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getFeedback_ShouldReturnFeedback() throws Exception {
        Mockito.when(feedbackQueryService.findById(feedbackId)).thenReturn(feedback);
        Mockito.when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/feedbacks/query/{id}", feedbackId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$.title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$.comment").value(feedbackResponse.getComment()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getFeedbackByUser_ShouldReturnFeedbackList() throws Exception {
        UUID userId = UUID.randomUUID();
        Mockito.when(feedbackQueryService.findByUserId(userId)).thenReturn(List.of(feedback));
        Mockito.when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/feedbacks/query/{id}/by_user", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$[0].title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$[0].comment").value(feedbackResponse.getComment()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getFeedbacks_ShouldReturnPaginatedFeedbacks() throws Exception {
        UUID targetId = UUID.randomUUID();
        Mockito.when(feedbackQueryService.getAll(Mockito.any(GetAllFeedbacksByTargetQuery.class)))
                .thenReturn(feedbackPageModel);
        Mockito.when(feedbackResponseMapper.feedbackToResponse(feedback)).thenReturn(feedbackResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/feedbacks/query/{target_id}/pages?pageNumber=0&pageSize=1", targetId)
                        .param("pageNumber", "0")
                        .param("pageSize", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(feedbackResponse.getId().toString()))
                .andExpect(jsonPath("$.items[0].title").value(feedbackResponse.getTitle()))
                .andExpect(jsonPath("$.totalCount").value(feedbackPageModel.getTotalCount()));
    }
}
