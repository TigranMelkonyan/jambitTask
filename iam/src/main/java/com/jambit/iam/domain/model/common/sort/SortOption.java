package com.jambit.iam.domain.model.common.sort;

import lombok.Getter;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 7:50 PM
 */
@Getter
public enum SortOption {

    ASC("asc"),
    DESC("desc");

    private final String value;

    SortOption(final String value) {
        this.value = value;
    }
}
