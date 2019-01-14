package com.codecentric.retailbank.repository.JDBC.helpers;

import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;

import java.util.List;

public interface JDBCRepositoryBase<T, ID> {
    List<T> findAllOrDefault();

    List<T> findAll();

    ListPage<T> findAllRangeOrDefault(int pageIndex, int pageSize);

    ListPage<T> findAllRange(int pageIndex, int pageSize);

    T getSingleOrDefault(ID id);

    T getSingle(ID id);

    T add(T model);

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
