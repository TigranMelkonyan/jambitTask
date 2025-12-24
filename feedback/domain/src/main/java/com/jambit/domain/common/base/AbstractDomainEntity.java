package com.jambit.domain.common.base;

import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:45 PM
 */
public abstract class AbstractDomainEntity {
    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }
}
