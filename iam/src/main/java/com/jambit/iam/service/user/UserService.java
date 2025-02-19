package com.jambit.iam.service.user;

import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.common.page.PageModel;
import com.jambit.iam.domain.model.common.search.SearchProperties;
import com.jambit.iam.domain.model.user.CreateUserModel;
import com.jambit.iam.domain.model.user.UpdateUserModel;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 7:45 PM
 */
public interface UserService {

    User create(final CreateUserModel model);

    User getById(final UUID id);

    User getByUserName(final String userName);

    User update(final UUID id, final UpdateUserModel model);

    void delete(final UUID id, final boolean deleteFromDb);

    void inactivateUser(final UUID id);

    PageModel<User> search(final SearchProperties searchProperties);
}
