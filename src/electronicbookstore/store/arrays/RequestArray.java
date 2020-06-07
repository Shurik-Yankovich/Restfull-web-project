package electronicbookstore.store.arrays;

import electronicbookstore.storage.Book;
import electronicbookstore.store.BookRequest;

import java.util.Arrays;

import static electronicbookstore.store.Status.COMPLETED;
import static electronicbookstore.store.Status.NEW;

public class RequestArray {

    private static final int DEFAULT_CAPACITY = 10;

    private BookRequest[] array;
    private int length;

    public RequestArray(BookRequest[] array) {
        this.array = array;
        this.length = array.length;
    }

    public RequestArray(int count) {
        this.array = new BookRequest[count];
    }

    public RequestArray() {
        this.array = new BookRequest[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public void add(BookRequest element) {
            if (length == array.length) {
                increaseArrayLength();
            }
            array[length] = (BookRequest) element;
            length++;
    }

    private void increaseArrayLength() {
        array = Arrays.copyOf(array, array.length + DEFAULT_CAPACITY);
    }

    public int size() {
        return length;
    }

    public BookRequest get(int index) {
        if (index >= 0 && index < length) {
            return array[index];
        }
        return null;
    }

    public void closeRequest(Book book) {
        for (int i = 0; i < length; i++) {
            if (array[i].getBook().equals(book) && array[i].getStatus() == NEW) {
                array[i].setStatus(COMPLETED);
            }
        }
    }
}
