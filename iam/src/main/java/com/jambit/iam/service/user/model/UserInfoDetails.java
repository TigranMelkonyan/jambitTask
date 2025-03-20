package com.jambit.iam.service.user.model;

import com.jambit.iam.domain.model.common.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 2:21 PM
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDetails {

    private String userId;
    private String email;
    private Role role;
}
