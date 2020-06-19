package bookstore.repository.request;

import bookstore.model.Book;
import bookstore.model.Request;
import bookstore.model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bookstore.model.Status.COMPLETED;
import static bookstore.model.Status.NEW;

public class ListRequestRepository implements RequestRepository {

    private List<Request> array;

    public ListRequestRepository(Request... array) {
        this.array = Arrays.asList(array);
    }

    public ListRequestRepository(List<Request> array) {
        this.array = array;
    }

    public ListRequestRepository() {
        this.array = new ArrayList<>();
    }

    @Override
    public String toString() {
        return array.toString();
    }

    @Override
    public int add(Request element) {
        element.setNumber(array.size());
        array.add(element);
        changeCountByBook(element.getBook());
        return array.size() - 1;
    }

    private void changeCountByBook(Book book) {
        int count = getCountRequests(book);
        for (Request request: array) {
            if (request.getBook().equals(book)) {
                request.setCount(count);
            }
        }
    }

    private int getCountRequests(Book book) {
        int result = 0;
        for (Request request : array) {
            if (book.equals(request.getBook())) {
                result++;
            }
        }
        return result;
    }

    @Override
    public void completeRequest(Book book) {
        for (Request request: array) {
            if (request.getBook().equals(book) && request.getStatus() == NEW) {
                request.setStatus(COMPLETED);
            }
        }
    }

    @Override
    public boolean changeStatus(int numberRequest, Status status) {
        Request request = getByRequestNumber(numberRequest);
        if (request != null) {
            request.setStatus(status);
            return true;
        }
        return false;
    }

    @Override
    public Request getByRequestNumber(int requestNumber) {
        Request request = null;
        if (requestNumber >= 0 && requestNumber < array.size()) {
            request = searchByRequestNumber(requestNumber);
        }
        return request;
    }

    private Request searchByRequestNumber(int requestNumber) {
        for (Request request : array) {
            if (request.getNumber() == requestNumber) {
                return request;
            }
        }
        return null;
    }

    @Override
    public Request get(int index) {
        return array.get(index);
    }

    @Override
    public List<Request> getAll() {
        return array;
    }

    @Override
    public int size() {
        return array.size();
    }
}
