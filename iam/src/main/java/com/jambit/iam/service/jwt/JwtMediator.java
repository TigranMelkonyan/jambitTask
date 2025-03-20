package com.jambit.iam.service.jwt;

import com.jambit.iam.domain.model.common.exception.ErrorCode;
import com.jambit.iam.domain.model.common.exception.RecordConflictException;
import com.jambit.iam.service.jwt.model.CreateUserTokenDto;
import com.jambit.iam.service.user.UserService;
import com.jambit.iam.service.user.mapper.UserDtoMapper;
import com.jambit.iam.service.user.model.UserDto;
import com.jambit.iam.service.user.model.UserInfoDetails;
import com.jambit.iam.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by Tigran Melkonyan
 * Date: 3/19/25
 * Time: 4:28 PM
 */
@Service
@RequiredArgsConstructor
public class JwtMediator {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDtoMapper userDtoMapper;


    public String grantToken(final CreateUserTokenDto request) {
        UserDto userDto = userDtoMapper.userToUserDto(userService.getByUserName(request.getUserName()));
        if (!PasswordUtils.isPasswordMatch(request.getPassword(), userDto.getPassword())) {
            throw new RecordConflictException("Invalid credentials", ErrorCode.INVALID_CREDENTIALS);
        }
        return jwtService.createJwt(new CreateUserTokenDto(request.getUserName(), request.getPassword()),
                new UserInfoDetails(userDto.getId().toString(), userDto.getEmail(), userDto.getRole()));
    }
}
