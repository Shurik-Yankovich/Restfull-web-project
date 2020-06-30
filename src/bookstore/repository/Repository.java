package bookstore.repository;

import java.util.List;

public interface Repository<T,P> {
    T create(T t);//+++
    T read(P p);//+++
    T update(T t, P p);//+++
    void delete(P p);//---
    List<T> readAll(List<P> p);//???
    void createAll(List<T> t);//---
}
