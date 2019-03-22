package com.codecentric.retailbank.repository.helpers;

import java.util.List;

public class ListPage<T> {

    private List<T> models;
    private long pageCount;
    private long modelsCount;


    public ListPage() {
    }

    public ListPage(List<T> modelList, long modelsCount, long pageSize) {
        this.models = modelList;
        this.modelsCount = modelsCount;
        this.pageCount = (modelsCount / pageSize) < 1.0f ?
                1L : (long) Math.ceil((modelsCount / pageSize) + 0.5f);
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
        this.modelsCount = modelsCount;
        this.pageCount = (modelsCount / pageSize) < 1.0f ?
                1L : (long) Math.ceil((modelsCount / pageSize) + 0.5f);
    }

    public long getModelsCount() {
        return modelsCount;
    }

    public void setModelsCount(long modelsCount) {
        this.modelsCount = modelsCount;
    }
}
