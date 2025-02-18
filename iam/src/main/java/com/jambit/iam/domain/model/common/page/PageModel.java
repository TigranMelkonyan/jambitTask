package com.jambit.iam.domain.model.common.page;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 7:44 PM
 */
@Getter
public class PageModel<T> {

    private final List<T> items = new ArrayList<>();
    private final long totalCount;

    public PageModel(List<T> items, long totalCount) {
        this.items.addAll(items);
        this.totalCount = totalCount;
    }
}
