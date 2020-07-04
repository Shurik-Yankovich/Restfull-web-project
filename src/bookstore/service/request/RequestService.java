package bookstore.service.request;

import bookstore.entity.book.Book;
import bookstore.entity.Request;

import java.util.List;

public interface RequestService {

    int addRequest(Book book);
    List<Integer> addRequestList(List<Book> books);
    void completeRequest(Book book);
    void cancelRequest(int number);
    boolean checkCompleteRequest(List<Integer> requestNumbers);
    Request getRequestByNumber(int requestNumber);
    List<Request> getRequestList();
    List<Request> getSortingRequestList();
    void readDataFromFile();
    void writeDataToFile();
}
