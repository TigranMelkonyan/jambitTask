package com.jambit.iam.conf.security.jwt;

import lombok.Getter;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 11:31 AM
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

    public String getValue() {
        return value;
    }
}
