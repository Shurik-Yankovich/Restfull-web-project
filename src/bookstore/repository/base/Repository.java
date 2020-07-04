package bookstore.repository.base;

import java.util.List;

public interface Repository<T, P, K, V> {
    T create(T t);
    T update(V v, P p);
    T read(K k);
    void delete(K k);
    List<T> readAll();
    void createAll(List<T> t);
}
