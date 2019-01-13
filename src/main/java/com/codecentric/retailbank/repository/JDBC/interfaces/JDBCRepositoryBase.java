package com.codecentric.retailbank.repository.JDBC.interfaces;

import com.codecentric.retailbank.repository.JDBC.ListPage;

import java.util.List;

public interface JDBCRepositoryBase<T, ID> {
    List<T> all();

    ListPage<T> allByPage(int pageIndex, int pageSize);

    T singleOrDefault(ID id);

    T single(ID id);

    T firstOrDefault(ID id);

    T first(ID id);

    T lastOrDefault(ID id);

    T last(ID id);

    T insert(T model);

    T update(T model);

    void delete(T model);

    void deleteById(T model);

    T insertMany(Iterable<T> model);

    T updateMany(Iterable<T> model);

    void deleteMany(Iterable<T> model);

    void deleteManyById(Iterable<ID> model);

}
