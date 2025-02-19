package com.jambit.iam.unit;

import com.jambit.iam.domain.entity.common.base.ModelStatus;
import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.common.role.Role;
import com.jambit.iam.domain.model.mapper.UserMapper;
import com.jambit.iam.domain.model.user.CreateUserModel;
import com.jambit.iam.domain.model.user.UpdateUserModel;
import com.jambit.iam.repository.user.UserRepository;
import com.jambit.iam.service.user.impl.UserServiceImpl;
import com.jambit.iam.service.validator.ModelValidator;
import com.jambit.iam.util.RandomEmailGenerator;
import com.jambit.iam.util.RandomNameGenerator;
import com.jambit.iam.util.RandomPassworGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 6:32 PM
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private Validator validator;

    @Mock
    private UserMapper mapper;

    private User user;
    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelValidator modelValidator = new ModelValidator(validator);
        userService = new UserServiceImpl(userRepository, modelValidator, mapper);
        String name = RandomNameGenerator.generateName();
        String email = RandomEmailGenerator.generateEmail();
        String pass = RandomPassworGenerator.generatePassword();
        user = new User(name, email, pass, Role.USER, true);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        String name = RandomNameGenerator.generateName();
        String email = RandomEmailGenerator.generateEmail();
        String pass = RandomPassworGenerator.generatePassword();
        CreateUserModel model = new CreateUserModel(name, email, pass, Role.USER);

        when(mapper.createModelToEntity(any())).thenAnswer(invocation -> {
            CreateUserModel inputModel = invocation.getArgument(0);
            User user = new User();
            user.setUsername(inputModel.getUsername());
            user.setEmail(inputModel.getEmail());
            return user;
        });
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User createdUser = userService.create(model);

        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getById_ShouldReturnUser_WhenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getById(userId);

        assertNotNull(foundUser, "User should not be null");
        assertEquals(user.getId(), foundUser.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        String name = RandomNameGenerator.generateName();
        String email = RandomEmailGenerator.generateEmail();
        UpdateUserModel model = new UpdateUserModel(name, email, Role.USER);

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setEmail("oldEmail@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        when(mapper.updateModelToEntity(any(UpdateUserModel.class), eq(existingUser))).thenAnswer(invocation -> {
            UpdateUserModel inputModel = invocation.getArgument(0);
            existingUser.setUsername(inputModel.getUsername());
            existingUser.setEmail(inputModel.getEmail());
            return existingUser;
        });

        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.update(userId, model);

        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(model.getUsername(), updatedUser.getUsername(), "Username should be updated");
        assertEquals(model.getEmail(), updatedUser.getEmail(), "Email should be updated");

        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void deleteUser_ShouldSoftDelete_WhenDeleteFromDbIsFalse() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId, false);

        assertEquals(user.getStatus(), ModelStatus.DELETED);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_ShouldHardDelete_WhenDeleteFromDbIsTrue() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId, true);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void inactivateUser_ShouldSetActiveToFalse() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.inactivateUser(userId);

        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);
    }
}

