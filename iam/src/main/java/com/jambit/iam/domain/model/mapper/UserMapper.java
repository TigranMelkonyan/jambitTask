package com.jambit.iam.domain.model.mapper;

import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.user.CreateUserModel;
import com.jambit.iam.domain.model.user.UpdateUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 8:54 PM
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "deletedOn", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "active", ignore = true)
    User createModelToEntity(CreateUserModel model);

    User updateModelToEntity(UpdateUserModel model);

    default String encodePassword(String password) {
        return password;
    }
}
