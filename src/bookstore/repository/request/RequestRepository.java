package bookstore.repository.request;

import bookstore.model.Request;
import bookstore.model.Status;
import bookstore.repository.Repository;

public interface RequestRepository extends Repository<Request, Status, Integer, Integer> {
}
