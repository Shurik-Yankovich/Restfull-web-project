package electronicbookstore.store.arrays;

import electronicbookstore.store.BookOrder;
import electronicbookstore.store.Status;

import java.util.Arrays;

public class OrderArray {

    private static final int DEFAULT_CAPACITY = 10;
    private static final String ORDER_NOT_FOUND = "Такого заказа не существует!";

    private BookOrder[] array;
    private int length;

    public OrderArray(BookOrder[] array) {
        this.array = array;
        this.length = array.length;
    }

    public OrderArray(int count) {
        this.array = new BookOrder[count];
    }

    public OrderArray() {
        this.array = new BookOrder[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public void add(BookOrder element) {
        if (length == array.length) {
            increaseArrayLength();
        }
        array[length] = element;
        length++;
    }

    private void increaseArrayLength() {
        array = Arrays.copyOf(array, array.length + DEFAULT_CAPACITY);
    }

    public int size() {
        return length;
    }

    public BookOrder get(int index) {
        if (index >= 0 && index < length) {
            return array[index];
        }
        return null;
    }

    public void changeOrderStatus(BookOrder bookOrder, Status status) {
        int index = searchOrderIndex(bookOrder);
        if (index >= 0) {
            array[index].setStatus(status);
        } else{
            System.out.println(ORDER_NOT_FOUND);
        }
    }

    private int searchOrderIndex(BookOrder bookOrder) {
        for (int i = 0; i < length; i++) {
            if (array[i].equals(bookOrder)) {
                return i;
            }
        }
        return -1;
    }

}
