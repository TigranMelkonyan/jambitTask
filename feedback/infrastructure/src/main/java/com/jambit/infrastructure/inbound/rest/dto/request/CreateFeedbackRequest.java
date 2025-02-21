package com.jambit.infrastructure.inbound.rest.dto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 8:18 PM
 */
@Data
public class CreateFeedbackRequest {

    @NotNull(message = "targetId required")
    private UUID feedbackTargetId;

    @NotBlank(message = "title required")
    @Size(max = 50)
    private String title;

    @Size(max = 1000)
    private String comment;

    @Min(0)
    @Max(10)
    private short score;
}
