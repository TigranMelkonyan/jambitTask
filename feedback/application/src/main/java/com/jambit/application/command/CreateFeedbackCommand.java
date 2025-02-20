package com.jambit.application.command;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 11:18 AM
 */
@Data
public class CreateFeedbackCommand {

    @NotNull(message = "targetId required")
    private UUID feedbackTargetId;

    @NotNull(message = "userId required")
    private UUID userId;

    @NotBlank(message = "title required")
    @Size(max = 50)
    private String title;

    @Size(max = 1000)
    private String comment;

    @NotNull
    private short score;

}
