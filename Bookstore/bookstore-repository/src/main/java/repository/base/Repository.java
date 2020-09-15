package repository.base;

import exeption.RepositoryException;

import java.util.List;

public interface Repository<T, PK> {
    T create(T t) throws RepositoryException;
    T update(T t) throws RepositoryException;
    T read(PK pk) throws RepositoryException;
    void delete(PK pk) throws RepositoryException;
    List<T> readAll() throws RepositoryException;
    void createAll(List<T> t) throws RepositoryException;
}
