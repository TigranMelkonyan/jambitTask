package com.jambit.iam.service.user.model;

import com.jambit.iam.domain.model.common.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 3/19/25
 * Time: 4:51 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;
    private String email;
    private String password;
    private Role role;
    
}
