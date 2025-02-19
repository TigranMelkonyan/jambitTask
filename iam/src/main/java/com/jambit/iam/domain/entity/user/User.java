package com.jambit.iam.domain.entity.user;

import com.jambit.iam.domain.entity.common.audit.AuditableBaseEntity;
import com.jambit.iam.domain.model.common.role.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.StringJoiner;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 4:27 PM
 */
@Entity
@Table(name = "iam_user")
@Getter
@Setter
public class User extends AuditableBaseEntity {
    
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "boolean default true")
    private boolean active = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("email='" + email + "'")
                .add("role=" + role)
                .add("active=" + active)
                .toString();
    }
}
