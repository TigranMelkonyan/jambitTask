package com.jambit.iam.controller.rest.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 12:20 PM
 */
@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserTokenRequest {
    
    @NotNull(message = "userName required")
    private final String userName;
    
    @NotNull(message = "userId required")
    private final String password;
    
    @NotNull
    private final UserInfoDetails info;
    
}
