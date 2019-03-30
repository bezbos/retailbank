package com.codecentric.retailbank.web.controller.api.v1.helpers;

import java.util.List;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

public class PageableList<T> {
    public Long nextPage;
    public Long currentPage;
    public Long previousPage;
    public Long pageCount;
    public List<T> items;

    public PageableList(List<T> items) {
        this.items = items;
    }

    public PageableList(long currentPage, List<T> items, Long pageCount, Long modelsCount) {
        if (items.size() < 1) {
            this.currentPage = null;
            this.nextPage = null;
            this.previousPage = null;
            this.pageCount = null;
        } else {
            //region EDGE CASE HANDLER
            // Magic!
            int increment = 1;
            if (pageCount > 2)
                increment = 2;
            if(pageCount > 2 && currentPage == (pageCount - (increment-1)))
                increment = 1;
            if(currentPage == 0 && pageCount == 2)
                increment = 2;
            //endregion

            this.currentPage = currentPage;
            this.nextPage = (currentPage == (pageCount - increment) ? (((float) modelsCount % ((float) PAGE_SIZE)) != 0.0f) : true)
                    ? currentPage + 1
                    : null;
            this.previousPage = currentPage < 1 ? null : currentPage - 1;
            this.pageCount = pageCount - 1;
        }

        this.items = items;
    }
}
