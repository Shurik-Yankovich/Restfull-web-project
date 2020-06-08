package electronicbookstore.store.arrays;

import electronicbookstore.comparator.*;
import electronicbookstore.storage.Book;
import electronicbookstore.store.Status;

import java.util.Arrays;
import java.util.Comparator;

import static electronicbookstore.store.Status.COMPLETED;
import static electronicbookstore.store.Status.NEW;

public class RequestArray {

    private BookRequest[] array;
    private int length;

    public RequestArray(BookRequest[] array) {
        this.array = array;
        this.length = array.length;
    }

    public RequestArray() {
        this.array = new BookRequest[0];
        length = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public int add(BookRequest element) {
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
        for (BookRequest request : array) {
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
        BookRequest request = getByRequestNumber(numberRequest);
        if (request != null)
            request.setStatus(status);
    }

    public BookRequest getByRequestNumber(int requestNumber) {
        BookRequest request = null;
        if (requestNumber >= 0 && requestNumber < length) {
            request = searchByRequestNumber(requestNumber);
        }
        return request;
    }

    private BookRequest searchByRequestNumber(int requestNumber) {
        for (BookRequest request : array) {
            if (request.getNumber() == requestNumber) {
                return request;
            }
        }
        return null;
    }

    public BookRequest get(int index) {
        BookRequest request = null;
        if (index >= 0 && index < length) {
            request = array[index];
        }
        return request;
    }

    public BookRequest[] getArray() {
        return sortBookRequest();
    }

    public int size() {
        return length;
    }

    private BookRequest[] sortBookRequest() {
        BookRequest[] requests = array;
        Comparator<BookRequest> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
        Arrays.sort(requests, requestComp);
        return requests;
    }

    private void sortBookName(BookRequest[] requests) {
        Arrays.sort(requests, new RequestBookNameComparator());
    }

    private void sortStatus(BookRequest[] requests) {
        Arrays.sort(requests, new RequestStatusComparator());
    }

    private void sortCount(BookRequest[] requests) {
        Arrays.sort(requests, new RequestCountComparator());
    }


}
