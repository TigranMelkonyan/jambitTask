package com.jambit.iam.repository.user.impl;

import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.common.page.PageModel;
import com.jambit.iam.domain.model.common.page.PageRequest;
import com.jambit.iam.domain.model.common.search.SearchProperties;
import com.jambit.iam.repository.user.UserRepositoryCustom;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 8:01 PM
 */
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageModel<User> search(final SearchProperties searchProperties) {
        TypedQuery<User> query = createQuery(searchProperties, "u", User.class, false);
        TypedQuery<Long> countQuery = createQuery(searchProperties, "count(u)", Long.class, true);
        PageRequest pageRequest = PageRequest.getPageRequestOrDefault(searchProperties.getPageRequest());
        query.setFirstResult(pageRequest.getPage());
        query.setMaxResults(pageRequest.getSize());
        List<User> user = query.getResultList();
        return new PageModel<>(user, countQuery.getSingleResult());
    }

    private <T> TypedQuery<T> createQuery(
            final SearchProperties searchProperties, final String selectCondition,
            final Class<T> type, boolean count) {
        Map<String, Object> appliedParams = new HashMap<>();
        String prefix = " where ";
        String queryString = " select " + selectCondition + " from User u ";
        if (Objects.nonNull(searchProperties) && StringUtils.hasText(searchProperties.getSearchText())) {
            queryString = queryString + prefix + "u.username like :search or u.email like :search ";
            appliedParams.put("search", "%" + searchProperties.getSearchText() + "%");
            prefix = " and ";
        }
        queryString = queryString + prefix + " u.status=:status ";
        appliedParams.put("status", searchProperties.getStatus());
        String sortOrder = "asc";
        if (Objects.nonNull(searchProperties.getSort())) {
            sortOrder = searchProperties.getSort().getValue();
        }
        if (Objects.nonNull(searchProperties.getSort()) && !count) {
            String orderByClause = " order by " + " " + sortOrder;
            queryString += orderByClause;
        }
        
        TypedQuery<T> query = entityManager.createQuery(queryString, type);
        appliedParams.forEach(query::setParameter);
        return query;
    }
}
