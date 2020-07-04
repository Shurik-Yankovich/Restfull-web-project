package bookstore.service.request;

import bookstore.model.book.Book;
import bookstore.model.Request;
import bookstore.repository.list.BookRequestRepository;
import bookstore.repository.base.RequestRepository;
import bookstore.util.comparator.RequestBookNameComparator;
import bookstore.util.comparator.RequestCountComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.model.Status.*;

public class BookRequestService implements RequestService {

    private RequestRepository requestList;

    public BookRequestService() {
        requestList = new BookRequestRepository();
    }

    @Override
    public int addRequest(Book book) {
        Request request = createNewRequest(book);
        return requestList.create(request).getId();
    }

    private Request createNewRequest(Book book) {
        Request request = new Request(book);
        request.setId(requestList.readAll().size());
        int count = changeCountByBook(book);
        request.setCount(count);
        return request;
    }

    private int changeCountByBook(Book book) {
        int count = getCountRequests(book);
        for (Request request: requestList.readAll()) {
            if (request.getBook().equals(book)) {
                request.setCount(count);
            }
        }
        return count;
    }

    private int getCountRequests(Book book) {
        int result = 0;
        for (Request request : requestList.readAll()) {
            if (book.equals(request.getBook())) {
                result++;
            }
        }
        return result;
    }

    @Override
    public List<Integer> addRequestList(List<Book> books) {
        List<Integer> requestNumbers = new ArrayList<>();
        for (Book book : books) {
            requestNumbers.add(addRequest(book));
        }
        return requestNumbers;
    }

    @Override
    public void completeRequest(Book book) {
        for (Request request: requestList.readAll()) {
            if (request.getBook().equals(book) && request.getStatus() == NEW) {
                request.setStatus(COMPLETED);
            }
        }
    }

    @Override
    public void cancelRequest(int number) {
        requestList.update(number, CANCELED);
    }

    @Override
    public boolean checkCompleteRequest(List<Integer> requestNumbers) {
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
    public Request getRequestByNumber(int requestNumber) {
        return requestList.read(requestNumber);
    }

    @Override
    public List<Request> getRequestList() {
        return requestList.readAll();
    }

    @Override
    public List<Request> getSortingRequestList() {
        List<Request> requests = new ArrayList<>(requestList.readAll());
        if (requests.size() > 0) {
            Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
            requests.sort(requestComp);
        }
        return requests;
    }
}
