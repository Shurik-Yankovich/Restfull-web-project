package bookstore.repository.request;

import bookstore.model.book.Book;
import bookstore.model.Request;
import bookstore.model.Status;

import java.util.List;

public interface RequestRepository {

    int add(Request element);
    void completeRequest(Book book);
    boolean changeStatus(int numberRequest, Status status);
    Request getByRequestNumber(int requestNumber);
    Request get(int index);
    List<Request> getAll();
    int size();
}
