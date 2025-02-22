package com.jambit.domain.common.page;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:27 AM
 */
@Getter
@ToString
public class PageModel<T> {

    private final List<T> items = new ArrayList<>();
    private final long totalCount;

    public PageModel(List<T> items, long totalCount) {
        this.items.addAll(items);
        this.totalCount = totalCount;
    }
}
