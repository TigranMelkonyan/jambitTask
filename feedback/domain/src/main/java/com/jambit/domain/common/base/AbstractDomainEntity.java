package com.jambit.domain.common.base;

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
@MappedSuperclass
public abstract class AbstractDomainEntity {

    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "UUID")
    @GeneratedValue

    @Id
    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }
}
