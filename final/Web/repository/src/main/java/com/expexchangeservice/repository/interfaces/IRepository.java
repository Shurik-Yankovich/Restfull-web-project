package com.expexchangeservice.repository.interfaces;

import java.util.List;

public interface IRepository<T> {
    T create(T t);

    void update(T t);

    T read(Integer pk);

    void delete(Integer pk);

    List<T> readAll();
}
