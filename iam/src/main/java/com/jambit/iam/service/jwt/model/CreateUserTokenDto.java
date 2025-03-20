package com.jambit.iam.service.jwt.model;

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
public class CreateUserTokenDto {

    @NotNull(message = "userName required")
    private final String userName;

    @NotNull(message = "userId required")
    private final String password;
    
}
