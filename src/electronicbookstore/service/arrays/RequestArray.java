package electronicbookstore.service.arrays;

import electronicbookstore.util.comparator.*;
import electronicbookstore.model.Book;
import electronicbookstore.model.Request;
import electronicbookstore.model.Status;

import java.util.Arrays;
import java.util.Comparator;

import static electronicbookstore.model.Status.COMPLETED;
import static electronicbookstore.model.Status.NEW;

public class RequestArray {

    private Request[] array;
    private int length;

    public RequestArray(Request[] array) {
        this.array = array;
        this.length = array.length;
    }

    public RequestArray() {
        this.array = new Request[0];
        length = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public int add(Request element) {
        increaseArrayLength();
        element.setNumber(length);
        array[length] = element;
        length++;
        changeCountByBook(element.getBook());
        return length - 1;
    }

    private void increaseArrayLength() {
        array = Arrays.copyOf(array, array.length + 1);
    }

    private void changeCountByBook(Book book) {
        int count = getCountRequests(book);
        for (int i = 0; i < length; i++) {
            if (array[i].getBook().equals(book)) {
                array[i].setCount(count);
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
        for (int i = 0; i < length; i++) {
            if (array[i].getBook().equals(book) && array[i].getStatus() == NEW) {
                array[i].setStatus(COMPLETED);
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
        if (requestNumber >= 0 && requestNumber < length) {
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
        Request request = null;
        if (index >= 0 && index < length) {
            request = array[index];
        }
        return request;
    }

    public Request[] getArray() {
        return sortBookRequest();
    }

    public int size() {
        return length;
    }

    private Request[] sortBookRequest() {
        Request[] requests =  Arrays.copyOf(array, length);
        Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
        Arrays.sort(requests, requestComp);
        return requests;
    }

    private void sortBookName(Request[] requests) {
        Arrays.sort(requests, new RequestBookNameComparator());
    }

    private void sortStatus(Request[] requests) {
        Arrays.sort(requests, new RequestStatusComparator());
    }

    private void sortCount(Request[] requests) {
        Arrays.sort(requests, new RequestCountComparator());
    }


}
