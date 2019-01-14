package com.codecentric.retailbank.repository.JDBC.helpers;

import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;

import java.util.List;

public interface JDBCRepositoryBase<T, ID> {
    List<T> all();

    ListPage<T> allByPage(int pageIndex, int pageSize);

    T singleOrDefault(ID id);

    T single(ID id);

    T insert(T model);

    T update(T model);

    void deleteOrDefault(T model);

    void delete(T model);

    void deleteByIdOrDefault(Long id);

    void deleteById(Long id);

    void insertBatch(Iterable<T> models);

    void updateBatch(Iterable<T> models);

    void deleteBatch(Iterable<T> models);

    void deleteBatchByIds(Iterable<ID> ids);

}
