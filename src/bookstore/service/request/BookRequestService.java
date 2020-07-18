package bookstore.service.request;

import bookstore.entity.Request;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.RequestRepository;
import bookstore.repository.file.FileRequestRepository;
import bookstore.repository.list.BookRequestRepository;
import bookstore.util.comparator.RequestBookNameComparator;
import bookstore.util.comparator.RequestCountComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.entity.Status.*;

public class BookRequestService implements RequestService {

    private RequestRepository requestRepository;
    private FileRequestRepository fileRequestRepository;

    public BookRequestService() {
        requestRepository = new BookRequestRepository();
        fileRequestRepository = new FileRequestRepository();
    }

    @Override
    public Request addRequest(Book book) throws RepositoryException{
        Request request = createNewRequest(book);
        return requestRepository.create(request);
    }

    private Request createNewRequest(Book book) throws RepositoryException{
        Request request = new Request(book);
//        request.setId(requestRepository.readAll().size());
        int count = changeCountByBook(book);
        request.setCount(count);
        return request;
    }

    private int changeCountByBook(Book book) throws RepositoryException  {
        int count = getCountRequests(book);
        for (Request request : requestRepository.readAll()) {
            if (request.getBook().equals(book)) {
                request.setCount(count);
            }
        }
        return count;
    }

    private int getCountRequests(Book book) throws RepositoryException {
        int result = 0;
        for (Request request : requestRepository.readAll()) {
            if (book.equals(request.getBook())) {
                result++;
            }
        }
        return result;
    }

    @Override
    public List<Integer> addRequestList(List<Book> books) throws RepositoryException{
        List<Integer> requestNumbers = new ArrayList<>();
        for (Book book : books) {
            requestNumbers.add(addRequest(book).getId());
        }
        return requestNumbers;
    }

    @Override
    public List<Request> completeRequest(Book book) throws RepositoryException{
        List<Request> requestList = new ArrayList<>();
        for (Request request : requestRepository.readAll()) {
            if (request.getBook().equals(book) && request.getStatus() == NEW) {
                request.setStatus(COMPLETED);
                requestList.add(request);
            }
        }
        return requestList;
    }

    @Override
    public Request cancelRequest(int number) throws RepositoryException{
        return requestRepository.update(number, CANCELED);
    }

    @Override
    public boolean checkCompleteRequest(List<Integer> requestNumbers) throws RepositoryException{
        if (requestNumbers != null) {
            for (int number : requestNumbers) {
                if (getRequestByNumber(number).getStatus() != COMPLETED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Request getRequestByNumber(int requestNumber) throws RepositoryException{
        return requestRepository.read(requestNumber);
    }

    @Override
    public List<Request> getRequestList() throws RepositoryException{
        return requestRepository.readAll();
    }

    @Override
    public List<Request> getSortingRequestList() throws RepositoryException{
        List<Request> requests = new ArrayList<>(requestRepository.readAll());
        if (requests.size() > 0) {
            Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
            requests.sort(requestComp);
        }
        return requests;
    }

    @Override
    public void readAllFromFile() throws RepositoryException{
        List<Request> requests = fileRequestRepository.readAll();
        requestRepository.createAll(requests);
    }

    @Override
    public void writeAllToFile() throws RepositoryException{
        fileRequestRepository.createAll(requestRepository.readAll());
    }

    @Override
    public void writeRequestToFile(Request request) throws RepositoryException{
        fileRequestRepository.create(request);
    }

    @Override
    public void updateRequestToFile(Request request) throws RepositoryException{
        fileRequestRepository.update(request, null);
    }
}
