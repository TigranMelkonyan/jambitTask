package com.jambit.iam.domain.model.user;

import com.jambit.iam.domain.model.common.role.Role;
import com.jambit.iam.domain.model.common.validate.ValidatableModel;
import com.jambit.iam.domain.model.common.validate.password.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 5:15 PM
 */
@Data
public class CreateUserModel implements ValidatableModel {

    @NotEmpty(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Please provide a valid email address")
    private String email;

    @ValidPassword
    private String password;

    @NotNull(message = "Rolle is required")
    private Role role;

    private boolean active = true;
}
