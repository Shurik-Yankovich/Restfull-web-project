package electronicbookstore.repository.request;

import electronicbookstore.model.Request;
import electronicbookstore.util.comparator.*;
import electronicbookstore.model.Book;
import electronicbookstore.model.Status;

import java.util.*;

import static electronicbookstore.model.Status.COMPLETED;
import static electronicbookstore.model.Status.NEW;

public class RequestArray {

    private List<Request> array;

    public RequestArray(Request... array) {
        this.array = Arrays.asList(array);
    }

    public RequestArray(List<Request> array) {
        this.array = array;
    }

    public RequestArray() {
        this.array = new ArrayList<>();
    }

    @Override
    public String toString() {
        return array.toString();
    }

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

    public void closeRequest(Book book) {
        for (Request request: array) {
            if (request.getBook().equals(book) && request.getStatus() == NEW) {
                request.setStatus(COMPLETED);
            }
        }
    }

    public void changeStatus(int numberRequest, Status status) {
        Request request = getByRequestNumber(numberRequest);
        if (request != null)
            request.setStatus(status);
    }

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

    public Request get(int index) {
        return array.get(index);
    }

    public List<Request> getArray() {
        return array;
    }

    public List<Request> getSortingArray() {
        return sortBookRequest();
    }

    public int size() {
        return array.size();
    }

    private List<Request> sortBookRequest() {
        List<Request> requests = new ArrayList<>(array);
        Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
        requests.sort(requestComp);
        return requests;
    }

    private void sortBookName(List<Request> requests) {
        requests.sort(new RequestBookNameComparator());
    }

    private void sortStatus(List<Request> requests) {
        requests.sort(new RequestStatusComparator());
    }

    private void sortCount(List<Request> requests) {
        requests.sort(new RequestCountComparator());
    }


}
