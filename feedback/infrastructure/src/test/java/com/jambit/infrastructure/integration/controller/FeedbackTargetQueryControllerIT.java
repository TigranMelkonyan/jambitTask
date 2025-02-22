package com.jambit.infrastructure.integration.controller;

import com.jambit.application.query.GetAllFeedbackTargetsQuery;
import com.jambit.application.service.FeedbackTargetQueryService;
import com.jambit.domain.common.page.PageModel;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.infrastructure.inbound.rest.dto.response.FeedbackTargetResponse;
import com.jambit.infrastructure.inbound.rest.mapper.FeedbackTargetResponseMapper;
import com.jambit.infrastructure.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tigran Melkonyan
 * Date: 2/22/25
 * Time: 5:31 PM
 */
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"com.jambit.infrastructure.config.security"})
class FeedbackTargetQueryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackTargetQueryService feedbackTargetQueryService;

    @MockBean
    private FeedbackTargetResponseMapper feedbackTargetResponseMapper;

    private UUID feedbackTargetId;
    private FeedbackTargetResponse feedbackTargetResponse;

    @BeforeEach
    void setUp() {
        FeedbackTarget feedbackTarget = TestUtils.createFeedbackTarget();
        feedbackTargetId = feedbackTarget.getId();

        feedbackTargetResponse = TestUtils.createFeedbackTargetResponse(feedbackTargetId);

        Mockito.when(feedbackTargetQueryService.findById(feedbackTargetId))
                .thenReturn(feedbackTarget);

        Mockito.when(feedbackTargetQueryService.getAll(Mockito.any(GetAllFeedbackTargetsQuery.class)))
                .thenReturn(new PageModel<>(List.of(feedbackTarget), 1));

        Mockito.when(feedbackTargetResponseMapper.feedbackTargetToResponse(feedbackTarget))
                .thenReturn(feedbackTargetResponse);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getFeedbackTarget_ShouldReturnFeedbackTarget() throws Exception {
        mockMvc.perform(get("/api/feedback_targets/query/{id}", feedbackTargetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackTargetResponse.getId().toString()))
                .andExpect(jsonPath("$.name").value(feedbackTargetResponse.getName()));

        Mockito.verify(feedbackTargetQueryService, Mockito.times(1))
                .findById(feedbackTargetId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getFeedbackTargets_ShouldReturnPaginatedFeedbackTargets() throws Exception {
        mockMvc.perform(get("/api/feedback_targets/query/pages")
                        .param("pageNumber", "0")
                        .param("pageSize", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(feedbackTargetResponse.getId().toString()))
                .andExpect(jsonPath("$.items[0].name").value(feedbackTargetResponse.getName()))
                .andExpect(jsonPath("$.totalCount").value(1));

        Mockito.verify(feedbackTargetQueryService, Mockito.times(1))
                .getAll(Mockito.any(GetAllFeedbackTargetsQuery.class));
    }
}
