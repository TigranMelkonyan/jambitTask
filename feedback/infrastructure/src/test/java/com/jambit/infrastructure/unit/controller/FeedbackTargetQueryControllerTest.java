package com.jambit.infrastructure.unit.controller;

import com.jambit.application.query.GetAllFeedbackTargetsQuery;
import com.jambit.application.service.FeedbackTargetQueryService;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.controller.feedback.FeedbackTargetQueryController;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackTargetResponse;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetResponseMapper;
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

import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 5:02 PM
 */
@WebMvcTest(FeedbackTargetQueryController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
class FeedbackTargetQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackTargetQueryService feedbackTargetQueryService;

    @MockBean
    private FeedbackTargetResponseMapper feedbackResponseMapper;

    private FeedbackTarget feedbackTarget;
    private FeedbackTargetResponse feedbackTargetResponse;
    private UUID feedbackTargetId;

    @BeforeEach
    void setUp() {
        feedbackTarget = TestUtils.createFeedbackTarget();

        feedbackTargetId = feedbackTarget.getId();

        feedbackTargetResponse = TestUtils.createFeedbackTargetResponse(feedbackTargetId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getFeedbackTarget_ShouldReturnFeedbackTarget() throws Exception {
        Mockito.when(feedbackTargetQueryService.findById(feedbackTargetId)).thenReturn(feedbackTarget);
        Mockito.when(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget)).thenReturn(feedbackTargetResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/feedback_targets/query/{id}", feedbackTargetId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackTargetResponse.getId().toString()))
                .andExpect(jsonPath("$.name").value(feedbackTargetResponse.getName()));

        Mockito.verify(feedbackTargetQueryService, times(1)).findById(feedbackTargetId);
        Mockito.verify(feedbackResponseMapper, times(1)).feedbackTargetToResponse(feedbackTarget);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getFeedbackTargets_ShouldReturnPaginatedFeedbackTargets() throws Exception {
        PageModel<FeedbackTarget> feedbackPage = new PageModel<>(List.of(feedbackTarget), 1);

        Mockito.when(feedbackTargetQueryService.getAll(Mockito.any(GetAllFeedbackTargetsQuery.class))).thenReturn(feedbackPage);
        Mockito.when(feedbackResponseMapper.feedbackTargetToResponse(feedbackTarget)).thenReturn(feedbackTargetResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/feedback_targets/query/pages")
                .param("pageNumber", "0")
                .param("pageSize", "1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(feedbackTargetResponse.getId().toString()))
                .andExpect(jsonPath("$.totalCount").value(1));

        Mockito.verify(feedbackTargetQueryService, VerificationModeFactory.times(1)).getAll(Mockito.any(GetAllFeedbackTargetsQuery.class));
        Mockito.verify(feedbackResponseMapper, VerificationModeFactory.times(1)).feedbackTargetToResponse(feedbackTarget);
    }
}

