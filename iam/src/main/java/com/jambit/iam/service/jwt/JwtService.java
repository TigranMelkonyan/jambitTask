package com.jambit.iam.service.jwt;

import com.jambit.iam.service.jwt.model.CreateUserTokenDto;
import com.jambit.iam.service.user.model.UserInfoDetails;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 12:12 PM
 */
public interface JwtService {
    String createJwt(final CreateUserTokenDto info, final UserInfoDetails userInfoDetails);
}
