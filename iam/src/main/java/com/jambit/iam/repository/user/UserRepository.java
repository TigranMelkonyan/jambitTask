package com.jambit.iam.repository.user;

import com.jambit.iam.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 7:58 PM
 */
public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
    
    boolean existsByEmail(final String email);
    
    boolean existsByUsername(final String userName);

    Optional<User> findByUsername(final String userName);
}
