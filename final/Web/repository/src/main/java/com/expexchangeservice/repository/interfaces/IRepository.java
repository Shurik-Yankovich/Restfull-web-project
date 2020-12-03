package com.expexchangeservice.repository.interfaces;

import com.expexchangeservice.model.exception.DBException;

import java.util.List;

public interface IRepository<T> {
    void create(T t);

    void update(T t);

    T read(Long pk);

    void delete(T t);

    List<T> readAll();
}
