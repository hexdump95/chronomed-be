package ar.sergiovillanueva.chronomed.dto;

import java.util.List;
import java.util.Collections;

public class PageResponse<T> {

    private final int pageIndex;
    private final int pageSize;
    private final long totalItems;
    private final int totalPages;
    private final List<T> items;

    private PageResponse(Builder<T> builder) {
        this.items = builder.items != null ? builder.items : Collections.emptyList();
        this.pageIndex = builder.pageIndex;
        this.pageSize = builder.pageSize;
        this.totalItems = builder.totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public static class Builder<T> {
        private List<T> items;
        private int pageIndex;
        private int pageSize;
        private long totalItems;

        public Builder<T> items(List<T> items) {
            this.items = items;
            return this;
        }

        public Builder<T> pageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
            return this;
        }

        public Builder<T> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder<T> totalItems(long totalItems) {
            this.totalItems = totalItems;
            return this;
        }

        public PageResponse<T> build() {
            return new PageResponse<>(this);
        }
    }
}
