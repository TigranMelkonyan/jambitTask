package com.jambit.iam.controller.mapper;

import com.jambit.iam.controller.rest.model.request.OauthTokenRequest;
import com.jambit.iam.service.jwt.model.CreateUserTokenDto;
import org.mapstruct.Mapper;

/**
 * Created by Tigran Melkonyan
 * Date: 3/20/25
 * Time: 11:21 AM
 */
@Mapper(componentModel = "spring")
public interface CreateUserTokenMapper {
    CreateUserTokenDto toDto(OauthTokenRequest request);
}

