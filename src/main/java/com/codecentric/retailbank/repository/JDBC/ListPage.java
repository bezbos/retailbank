package com.codecentric.retailbank.repository.JDBC;

import java.util.List;

public class ListPage<T> {
    private List<T> models;
    private long pageCount;


    public ListPage() {
    }

    public ListPage(List<T> modelList, long modelsCount, long pageSize) {
        this.models = modelList;
        this.pageCount = (modelsCount / pageSize) < 1.0f ?
                1 : (modelsCount / pageSize);
    }


    public List<T> getModels() {
        return models;
    }

    public void setModels(List<T> models) {
        this.models = models;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long modelsCount, long pageSize) {
        this.pageCount = (modelsCount / pageSize) < 1.0f ?
                1 : (modelsCount / pageSize);
    }
}
