package com.jambit.infrastructure.config.security.jwt;

import lombok.Getter;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 6:30 PM
 */
@Getter
public enum JwtClaim {
    EMAIL("email"),
    SUB("sub"),
    UID("uid"),
    EXPIRATION("exp"),
    AUTHORITIES("authorities");

    private final String value;

    JwtClaim(String value) {
        this.value = value;
    }
}
