package com.codecentric.retailbank.repository.helpers;

import java.util.List;

public interface JDBCRepositoryBase<T, ID> {
    List<T> findAll();

    ListPage<T> findAllRange(int pageIndex, int pageSize);

    T getSingle(ID id);

    T add(T model);

    T update(T model);

    void delete(T model);

    void deleteById(Long id);

    void insertBatch(Iterable<T> models);

    void updateBatch(Iterable<T> models);

    void deleteBatch(Iterable<T> models);

    void deleteBatchByIds(Iterable<ID> ids);

}
