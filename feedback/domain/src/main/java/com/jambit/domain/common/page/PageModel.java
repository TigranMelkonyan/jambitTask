package com.jambit.domain.common.page;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:27 AM
 */
public class PageModel<T> {

    private final List<T> items = new ArrayList<>();
    private final long totalCount;

    public PageModel(List<T> items, long totalCount) {
        this.items.addAll(items);
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PageModel.class.getSimpleName() + "[", "]")
                .add("items=" + items)
                .add("totalCount=" + totalCount)
                .toString();
    }
}
