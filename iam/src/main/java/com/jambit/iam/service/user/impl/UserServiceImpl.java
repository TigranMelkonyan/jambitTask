package com.jambit.iam.service.user.impl;

import com.jambit.iam.domain.entity.common.base.ModelStatus;
import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.common.exception.ErrorCode;
import com.jambit.iam.domain.model.common.exception.RecordConflictException;
import com.jambit.iam.domain.model.common.page.PageModel;
import com.jambit.iam.domain.model.common.search.SearchProperties;
import com.jambit.iam.domain.model.mapper.UserMapper;
import com.jambit.iam.domain.model.user.CreateUserModel;
import com.jambit.iam.domain.model.user.UpdateUserModel;
import com.jambit.iam.repository.user.UserRepository;
import com.jambit.iam.service.user.UserService;
import com.jambit.iam.service.user.validator.ModelValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 7:58 PM
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelValidator validator;
    private final UserMapper mapper;

    public UserServiceImpl(
            final UserRepository repository,
            final ModelValidator validator,
            final UserMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public User create(final CreateUserModel model) {
        log.info("Creating user with model - {} ", model);
        validator.validate(model);
        assertNotExistsWithEmail(model.getEmail());
        assertNotExistsWithUserName(model.getUsername());
        User entity = mapper.createModelToEntity(model);
        entity.setActive(true);
        User result = repository.save(entity);
        log.info("Successfully created user with model - {}, result - {}", model, result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(final UUID id) {
        log.info("Retrieving user with id - {} ", id);
        Assert.notNull(id, "id must not be null");
        User result = repository.getById(id);
        log.info("Successfully retrieved user with id - {}, result - {}", id, result);
        return result;
    }

    @Override
    @Transactional
    public User update(final UUID id, final UpdateUserModel model) {
        log.info("Updating user with id - {} ", id);
        Assert.notNull(id, "id must not be null");
        validator.validate(model);
        assertNotExistsWithEmail(model.getEmail());
        assertNotExistsWithUserName(model.getUsername());
        User entity = mapper.updateModelToEntity(model);
        User result = repository.save(entity);
        log.info("Successfully updated user with id - {}, result - {}", id, result);
        return result;
    }

    @Override
    @Transactional
    public void delete(final UUID id, final boolean deleteFromDb) {
        log.info("Deleting user with id - {} ", id);
        Assert.notNull(id, "id must not be null");
        if (deleteFromDb) {
            repository.deleteById(id);
            log.info("Successfully deleted user with id - {} from db", id);
        } else {
            User user = repository.getById(id);
            user.setStatus(ModelStatus.DELETED);
            repository.save(user);
            log.info("Successfully soft deleted user with id - {} ", id);
        }
        log.info("Successfully deleted user with id - {} deleteFromDb - {} ", id, deleteFromDb);
    }

    @Override
    @Transactional
    public void inactivateUser(final UUID id) {
        log.info("Inactivating user with id - {} ", id);
        Assert.notNull(id, "id must not be null");
        User user = repository.getById(id);
        user.setActive(false);
        User result = repository.save(user);
        log.info("Successfully inactivated user with id - {} result - {} ", id, result);

    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<User> search(final SearchProperties searchProperties) {
        log.info("Searching users with request - {}", searchProperties);
        validator.validate(searchProperties);
        PageModel<User> users = repository.search(searchProperties);
        log.info("Successfully retrieved users - {}", users);
        return new PageModel<>(users.getItems(), users.getTotalCount());
    }

    private void assertNotExistsWithEmail(final String email) {
        if (repository.existsByEmail(email)) {
            throw new RecordConflictException(String.format("User with email - %s already exists", email),
                    ErrorCode.EXISTS_EXCEPTION);
        }
    }

    private void assertNotExistsWithUserName(final String userName) {
        if (repository.existsByUsername(userName)) {
            throw new RecordConflictException(String.format("User with userName - %s already exists", userName),
                    ErrorCode.EXISTS_EXCEPTION);
        }
    }
}
