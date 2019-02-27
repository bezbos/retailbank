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
            //region EDGE CASE HANDLER
            // Handles the edge case when currentPage is 0.
            // It prevents assigning nextPage to (currentPage + 1)
            // even though there are no items on the next page
            int increment = 1;
            if (currentPage == 0)
                increment = 2;
            //endregion

            this.currentPage = currentPage;
            this.nextPage = (currentPage + increment) < pageCount
                    ? currentPage + 1
                    : null;
            this.previousPage = currentPage < 1 ? null : currentPage - 1;
        }

        this.items = items;
    }

}
