package com.jambit.iam.util;

import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 2/24/25
 * Time: 10:34 AM
 */
public final class NullCheckUtils {

    private NullCheckUtils() {
    }

    @SafeVarargs
    public static <T> void checkNullConstraints(List<String> names, T... object) {
        for (int i = 0; i < object.length; i++) {
            Assert.notNull(object[i], names.get(i) + " cannot be null");
        }
    }
}
