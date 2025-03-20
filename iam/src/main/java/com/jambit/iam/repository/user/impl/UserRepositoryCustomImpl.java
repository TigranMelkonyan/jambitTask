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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(searchProperties.getSearchText())) {
            Predicate usernamePredicate = cb.like(userRoot.get("username"), "%"
                    + searchProperties.getSearchText() + "%");
            Predicate emailPredicate = cb.like(userRoot.get("email"), "%"
                    + searchProperties.getSearchText() + "%");
            predicates.add(cb.or(usernamePredicate, emailPredicate));
        }

        if (Objects.nonNull(searchProperties.getStatus())) {
            predicates.add(cb.equal(userRoot.get("status"), searchProperties.getStatus()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        if (Objects.nonNull(searchProperties.getSort())) {
            if ("desc".equalsIgnoreCase(searchProperties.getSort().getValue())) {
                cq.orderBy(cb.desc(userRoot.get("username")));
            } else {
                cq.orderBy(cb.asc(userRoot.get("username")));
            }
        }

        TypedQuery<User> query = entityManager.createQuery(cq);
        PageRequest pageRequest = PageRequest.getPageRequestOrDefault(searchProperties.getPageRequest());
        query.setFirstResult(pageRequest.getPage() * pageRequest.getSize());
        query.setMaxResults(pageRequest.getSize());

        List<User> users = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageModel<>(users, count);
    }

}
