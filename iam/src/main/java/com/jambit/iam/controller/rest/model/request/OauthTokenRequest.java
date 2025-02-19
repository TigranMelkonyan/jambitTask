package com.jambit.iam.controller.rest.model.request;

import com.jambit.iam.domain.model.common.validate.ValidatableModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 12:59 PM
 */
@Getter
@Setter
public class OauthTokenRequest implements ValidatableModel {

    @NotBlank(message = "required")
    private String userName;

    @NotBlank(message = "required")
    private String password;
}

