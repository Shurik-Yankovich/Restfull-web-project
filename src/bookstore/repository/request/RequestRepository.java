package bookstore.repository.request;

import bookstore.model.Book;
import bookstore.model.Request;
import bookstore.model.Status;

import java.util.List;

public interface RequestRepository {

    int add(Request element);
    void closeRequest(Book book);
    void changeStatus(int numberRequest, Status status);
    Request getByRequestNumber(int requestNumber);
    Request get(int index);
    List<Request> getArray();
    int size();
}
