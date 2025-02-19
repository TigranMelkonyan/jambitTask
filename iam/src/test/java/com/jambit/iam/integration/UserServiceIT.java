package com.jambit.iam.integration;

import com.jambit.iam.domain.entity.common.base.ModelStatus;
import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.common.role.Role;
import com.jambit.iam.domain.model.user.CreateUserModel;
import com.jambit.iam.domain.model.user.UpdateUserModel;
import com.jambit.iam.repository.user.UserRepository;
import com.jambit.iam.service.user.UserService;
import com.jambit.iam.util.RandomEmailGenerator;
import com.jambit.iam.util.RandomNameGenerator;
import com.jambit.iam.util.RandomPassworGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 7:45 PM
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UUID userId = UUID.randomUUID();

    @BeforeAll
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void createUser_ShouldSaveUserToDatabase() {
        String name = RandomNameGenerator.generateName();
        String email = RandomEmailGenerator.generateEmail();
        String pass = RandomPassworGenerator.generatePassword();
        CreateUserModel model = new CreateUserModel(name, email, pass, Role.USER);
        User createdUser = userService.create(model);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(model.getUsername(), createdUser.getUsername());

        userId = createdUser.getId();
    }

    @Test
    @Order(2)
    void getById_ShouldReturnUserFromDatabase() {
        User user = userService.getById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    @Test
    @Order(3)
    void updateUser_ShouldModifyUserDetails() {
        User existingUser = userService.create(new CreateUserModel("oldName",
                "oldEmail@example.com", "SecurePass123!", Role.USER));

        String name = RandomNameGenerator.generateName();
        String email = RandomEmailGenerator.generateEmail();

        UpdateUserModel model = new UpdateUserModel(name, email, Role.USER);
        User updatedUser = userService.update(existingUser.getId(), model);

        assertEquals(model.getUsername(), updatedUser.getUsername());
        assertEquals(model.getEmail(), updatedUser.getEmail());
        assertEquals(existingUser.getPassword(), updatedUser.getPassword());
    }

    @Test
    @Order(4)
    void inactivateUser_ShouldReturnUserFromDatabase() {
        User user;

        userService.inactivateUser(userId);
        user = userService.getById(userId);

        assertFalse(user.isActive());
    }

    @Test
    @Order(5)
    void deleteUser_ShouldSoftDeleteUser() {
        String name = RandomNameGenerator.generateName();
        String email = RandomEmailGenerator.generateEmail();
        String pass = RandomPassworGenerator.generatePassword();
        User user = userService.create(new CreateUserModel(name,
                email, pass, Role.USER));

        UUID deletedUserId = user.getId();

        userService.delete(deletedUserId, false);

        User deletedUser = userService.getById(deletedUserId);
        assertEquals(deletedUser.getStatus(), ModelStatus.DELETED);
    }
}

