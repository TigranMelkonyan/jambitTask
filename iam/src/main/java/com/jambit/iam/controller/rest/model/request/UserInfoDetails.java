package com.jambit.iam.controller.rest.model.request;

import com.jambit.iam.domain.model.common.role.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 2:21 PM
 */
@Getter
@RequiredArgsConstructor
public class UserInfoDetails {

    private final String userId;
    private final String email;
    private final Role role;
}
