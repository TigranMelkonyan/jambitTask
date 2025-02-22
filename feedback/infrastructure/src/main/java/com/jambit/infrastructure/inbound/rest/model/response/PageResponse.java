package com.jambit.infrastructure.inbound.rest.model.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 11:10 PM
 */
@Getter
public class PageResponse<T> {

    private final List<T> items = new ArrayList<>();
    private final long totalCount;

    public PageResponse(List<T> items, long totalCount) {
        this.items.addAll(items);
        this.totalCount = totalCount;
    }
}
