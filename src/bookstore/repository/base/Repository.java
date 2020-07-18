package bookstore.repository.base;

import bookstore.exeption.RepositoryException;

import java.util.List;

public interface Repository<T, P, K, V> {
    T create(T t) throws RepositoryException;
    T update(V v, P p) throws RepositoryException;
    T read(K k) throws RepositoryException;
    void delete(K k) throws RepositoryException;
    List<T> readAll() throws RepositoryException;
    void createAll(List<T> t) throws RepositoryException;
}
