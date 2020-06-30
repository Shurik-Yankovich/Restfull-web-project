package bookstore.repository;

import java.util.List;

public interface Repository<T,P> {
    T create(T t);//+++
    T read(P p);//+++
    T update(T t, P p);//+++
    void delete(P p);//---
    List readAll(List p);//???
    void createAll(List t);//---
}
