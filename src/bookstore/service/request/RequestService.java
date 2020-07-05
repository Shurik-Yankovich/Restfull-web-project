package bookstore.service.request;

import bookstore.entity.Request;
import bookstore.entity.book.Book;

import java.util.List;

public interface RequestService {

    Request addRequest(Book book);
    List<Integer> addRequestList(List<Book> books);
    List<Request> completeRequest(Book book);
    Request cancelRequest(int number);
    boolean checkCompleteRequest(List<Integer> requestNumbers);
    Request getRequestByNumber(int requestNumber);
    List<Request> getRequestList();
    List<Request> getSortingRequestList();
    void readAllFromFile();
    void writeAllToFile();
    void writeRequestToFile(Request request);
    void updateRequestToFile(Request request);
}
