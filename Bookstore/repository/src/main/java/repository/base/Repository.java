package repository.base;

import exeption.RepositoryException;

import java.util.List;

public interface Repository<T, K> {
    T create(T t) throws RepositoryException;
    T update(T t) throws RepositoryException;
    T read(K k) throws RepositoryException;
    void delete(K k) throws RepositoryException;
    List<T> readAll() throws RepositoryException;
    void createAll(List<T> t) throws RepositoryException;
}
