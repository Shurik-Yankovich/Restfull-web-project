package bookstore.repository.db;

import bookstore.entity.Bookshelf;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.StorageRepository;
import bookstore.util.connections.ConnectionUtils;
import com.annotation.InjectByType;

import java.util.List;

public class DBStorageRepository implements StorageRepository {

    @InjectByType
    private ConnectionUtils connectionUtils;

    @Override
    public Bookshelf create(Bookshelf bookshelf) throws RepositoryException {
        return null;
    }

    @Override
    public Bookshelf update(Bookshelf bookshelf) throws RepositoryException {
        return null;
    }

    @Override
    public Bookshelf read(Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(Integer integer) throws RepositoryException {

    }

    @Override
    public List<Bookshelf> readAll() throws RepositoryException {
        return null;
    }

    @Override
    public void createAll(List<Bookshelf> t) throws RepositoryException {

    }
}
