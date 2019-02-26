package com.codecentric.retailbank.web.controller.api.v1.helpers;

import java.util.List;

public class PageableList<T> {
    public Long nextPage;
    public Long currentPage;
    public Long previousPage;
    public List<T> items;

    public PageableList() {
    }

    public PageableList(long currentPage, List<T> items, Long pageCount) {
        if (items.size() < 1) {
            this.currentPage = null;
            this.nextPage = null;
            this.previousPage = null;
        } else {
            this.currentPage = currentPage;
            this.nextPage = (currentPage + 1) < pageCount ? currentPage + 1 : null;
            this.previousPage = currentPage < 1 ? null : currentPage - 1;
        }

        this.items = items;
    }

}
