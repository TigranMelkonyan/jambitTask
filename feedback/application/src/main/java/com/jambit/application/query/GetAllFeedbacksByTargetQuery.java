package com.jambit.application.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 11:59 AM
 */
@Data
@AllArgsConstructor
public class GetAllFeedbacksByTargetQuery {

    @NotNull(message = "targetId required")
    private UUID targetId;

    @Min(0)
    private int page;

    @Min(1)
    private int size;

}
