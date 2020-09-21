package service.request;

import entity.Request;
import entity.Book;
import exeption.RepositoryException;

import java.util.List;

public interface RequestService {

    Request addRequest(Book book);

    List<Request> addRequestList(List<Book> books);

    List<Request> completeRequestsByBook(Book book);

    Request completeRequest(Request request);

    Request cancelRequest(Request request) throws RepositoryException;

    boolean checkCompleteRequest(List<Integer> requestNumbers);

    Request getRequestByNumber(int requestNumber);

    List<Request> getRequestList();

    List<Request> getNewRequests();

    List<Request> getSortingRequestList();

    boolean readAllFromFile();

    boolean writeAllToFile();

    boolean writeRequestToFile(Request request);

    boolean updateRequestToFile(Request request);
}
