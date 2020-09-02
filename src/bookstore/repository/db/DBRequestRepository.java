package bookstore.repository.db;

import bookstore.entity.Request;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.RequestRepository;
import bookstore.util.connections.ConnectionUtils;
import com.annotation.InjectByType;

import java.util.List;

public class DBRequestRepository implements RequestRepository {

    @InjectByType
    private ConnectionUtils connectionUtils;

    @Override
    public Request create(Request request) throws RepositoryException {
        return null;
    }

    @Override
    public Request update(Request request) throws RepositoryException {
        return null;
    }

    @Override
    public Request read(Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(Integer integer) throws RepositoryException {

    }

    @Override
    public List<Request> readAll() throws RepositoryException {
        return null;
    }

    @Override
    public void createAll(List<Request> t) throws RepositoryException {

    }
}
