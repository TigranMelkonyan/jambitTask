package com.jambit.iam.domain.model.mapper;

import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.user.CreateUserModel;
import com.jambit.iam.domain.model.user.UpdateUserModel;
import com.jambit.iam.util.PasswordUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 8:54 PM
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "deletedOn", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    User createModelToEntity(CreateUserModel model);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "deletedOn", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "active", ignore = true)
    User updateModelToEntity(UpdateUserModel model, @MappingTarget User entity);

    @Named("encodePassword")
    default String encodePassword(String password) {
        return PasswordUtils.encode(password);
    }
}
