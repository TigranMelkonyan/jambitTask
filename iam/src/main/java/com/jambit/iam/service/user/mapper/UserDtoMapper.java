package com.jambit.iam.service.user.mapper;

import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.service.user.model.UserDto;
import org.mapstruct.Mapper;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 8:54 PM
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto userToUserDto(User entity);

}
