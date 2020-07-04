package bookstore.repository.base;

import bookstore.model.Request;
import bookstore.model.Status;

public interface RequestRepository extends Repository<Request, Status, Integer, Integer> {
}
