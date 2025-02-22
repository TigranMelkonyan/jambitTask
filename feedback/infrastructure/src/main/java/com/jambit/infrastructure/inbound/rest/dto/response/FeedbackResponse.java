package com.jambit.infrastructure.inbound.rest.dto.response;

import lombok.Data;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:20 PM
 */
@Data
public class FeedbackResponse {

    private UUID id;
    private String title;
    private String comment;
    private short score;
    private UUID feedbackTargetId;
    
    //Should be replaced with user response data 
    private UUID userId;
}
