package com.jambit.iam.repository.user;

import com.jambit.iam.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 7:58 PM
 */
@Service
public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
    
    boolean existsByEmail(final String email);
    
    boolean existsByUsername(final String userName);
}
