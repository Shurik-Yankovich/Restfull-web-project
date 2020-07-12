package bookstore.service.request;

import bookstore.entity.Request;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;

import java.util.List;

public interface RequestService {

    Request addRequest(Book book) throws RepositoryException;
    List<Integer> addRequestList(List<Book> books) throws RepositoryException;
    List<Request> completeRequestsByBook(Book book) throws RepositoryException;
    Request completeRequest(Request request) throws RepositoryException;
    Request cancelRequest(int number) throws RepositoryException;
    boolean checkCompleteRequest(List<Integer> requestNumbers) throws RepositoryException;
    Request getRequestByNumber(int requestNumber) throws RepositoryException;
    List<Request> getRequestList() throws RepositoryException;
    List<Request> getNewRequests() throws RepositoryException;
    List<Request> getSortingRequestList() throws RepositoryException;
    void readAllFromFile() throws RepositoryException;
    void writeAllToFile() throws RepositoryException;
    void writeRequestToFile(Request request) throws RepositoryException;
    void updateRequestToFile(Request request) throws RepositoryException;
}
