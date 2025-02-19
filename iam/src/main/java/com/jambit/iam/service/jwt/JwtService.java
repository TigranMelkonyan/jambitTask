package com.jambit.iam.service.jwt;

import com.jambit.iam.controller.rest.model.request.CreateUserTokenRequest;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 12:12 PM
 */
public interface JwtService {
    String createJwt(CreateUserTokenRequest info);
}
