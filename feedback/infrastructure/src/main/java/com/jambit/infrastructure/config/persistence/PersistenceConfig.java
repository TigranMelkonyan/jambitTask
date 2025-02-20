package com.jambit.infrastructure.config.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 3:17 PM
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.jambit.infrastructure*"})
@EntityScan(basePackages = {"com.jambit.domain"})
public class PersistenceConfig {
}
