package bookstore.repository.file;

import bookstore.model.Request;
import bookstore.model.Status;
import bookstore.repository.base.RequestRepository;

import java.util.List;

public class FileRequestRepository implements RequestRepository {
    @Override
    public Request create(Request request) {
        return null;
    }

    @Override
    public Request update(Integer integer, Status status) {
        return null;
    }

    @Override
    public Request read(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Request> readAll() {
        return null;
    }

    @Override
    public void createAll(List<Request> t) {

    }
}
