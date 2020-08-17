package bookstore.service.request;

import bookstore.entity.Request;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;

import java.util.List;

public interface RequestService {

    Request addRequest(Book book);

    List<Integer> addRequestList(List<Book> books);

    List<Request> completeRequestsByBook(Book book);

    Request completeRequest(Request request);

    Request cancelRequest(int number) throws RepositoryException;

    boolean checkCompleteRequest(List<Integer> requestNumbers);

    Request getRequestByNumber(int requestNumber);

    List<Request> getRequestList();

    List<Request> getNewRequests();

    List<Request> getSortingRequestList();

    boolean readAllFromFile();

    boolean writeAllToFile();

    boolean writeRequestToFile(Request request);

    boolean updateRequestToFile(Request request);

    boolean save();
}
