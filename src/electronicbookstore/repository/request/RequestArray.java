package electronicbookstore.repository.request;

import electronicbookstore.model.Book;
import electronicbookstore.model.Request;
import electronicbookstore.model.Status;

import java.util.Arrays;

import static electronicbookstore.model.Status.COMPLETED;
import static electronicbookstore.model.Status.NEW;

public class RequestArray implements RequestRepository {

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

    @Override
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

    @Override
    public void closeRequest(Book book) {
        for (int i = 0; i < length; i++) {
            if (array[i].getBook().equals(book) && array[i].getStatus() == NEW) {
                array[i].setStatus(COMPLETED);
            }
        }
    }

    @Override
    public void changeStatus(int numberRequest, Status status) {
        Request request = getByRequestNumber(numberRequest);
        if (request != null)
            request.setStatus(status);
    }

    @Override
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

    @Override
    public Request get(int index) {
        Request request = null;
        if (index >= 0 && index < length) {
            request = array[index];
        }
        return request;
    }

    @Override
    public Request[] getArray() {
        return array;
    }

    @Override
    public int size() {
        return length;
    }
}
