package bookstore.service.request;

import bookstore.model.Book;
import bookstore.model.Request;
import bookstore.repository.request.ListRequestRepository;
import bookstore.repository.request.RequestRepository;
import bookstore.util.comparator.RequestBookNameComparator;
import bookstore.util.comparator.RequestCountComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.model.Status.CANCELED;
import static bookstore.model.Status.COMPLETED;

public class BookRequestService implements RequestService {

    private RequestRepository requestList;

    public BookRequestService() {
        requestList = new ListRequestRepository();
    }

    @Override
    public int addRequest(Book book) {
        return requestList.add(new Request(book));
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
        requestList.completeRequest(book);
    }

    @Override
    public void cancelRequest(int number) {
        requestList.changeStatus(number, CANCELED);
    }

    @Override
    public boolean checkCompleteRequest(List<Integer> requestNumbers) {
        if (requestNumbers != null) {
            for (int number : requestNumbers) {
                if (requestList.getByRequestNumber(number).getStatus() != COMPLETED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Request> getAll() {
        return requestList.getAll();
    }

    @Override
    public List<Request> getSortingRequests() {
        List<Request> requests = new ArrayList<>(requestList.getAll());
        if (requests.size() > 0) {
            Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
            requests.sort(requestComp);
        }
        return requests;
    }
}
