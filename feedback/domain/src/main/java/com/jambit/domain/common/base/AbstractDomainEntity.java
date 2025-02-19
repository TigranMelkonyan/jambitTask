package com.jambit.domain.common.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:45 PM
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractDomainEntity {

    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "UUID")
    @GeneratedValue

    @Id
    protected UUID id;
}
