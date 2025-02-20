package com.jambit.application.command;


import com.jambit.domain.feedback.TargetType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 11:18 AM
 */
@Data
public class CreateFeedbackTargetCommand {

    @NotNull(message = "targetType required")
    private TargetType targetType;

    @NotBlank(message = "title required")
    @Size(max = 100)
    private String name;
    
}
