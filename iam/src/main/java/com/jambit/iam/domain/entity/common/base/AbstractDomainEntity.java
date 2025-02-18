package com.jambit.iam.domain.entity.common.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 11/21/24
 * Time: 7:55 PM
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractDomainEntity {

    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    @GeneratedValue
    @Id
    protected UUID id;
}
