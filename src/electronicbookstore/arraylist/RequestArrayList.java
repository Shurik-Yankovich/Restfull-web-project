package electronicbookstore.arraylist;

import electronicbookstore.store.BookRequest;

import java.util.Arrays;

public class RequestArrayList implements MyArrayList {

    private static final int DEFAULT_CAPACITY = 10;

    private BookRequest[] array;
    private int length;

    public RequestArrayList(BookRequest[] array) {
        this.array = array;
        this.length = array.length;
    }

    public RequestArrayList(int count) {
        this.array = new BookRequest[count];
    }

    public RequestArrayList() {
        this.array = new BookRequest[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public void add(Object element) {
        if (length == array.length) {
            increaseArrayLength();
        }
        array[length] = (BookRequest) element;
        length++;
    }

    private void increaseArrayLength() {
        array = Arrays.copyOf(array, array.length + DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public BookRequest get(int index) {
        if (index >= 0 && index < length) {
            return array[index];
        } else {
            return null;
        }
    }
}
