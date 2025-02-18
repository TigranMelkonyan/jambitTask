package com.jambit.iam.repository.user;

import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.common.page.PageModel;
import com.jambit.iam.domain.model.common.search.SearchProperties;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 8:00 PM
 */
public interface UserRepositoryCustom {

    PageModel<User> search(final SearchProperties searchProperties);
}
