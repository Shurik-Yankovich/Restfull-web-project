package electronicbookstore.store.arrays;

import electronicbookstore.store.BookOrder;
import electronicbookstore.store.Status;

import java.util.Arrays;
import java.util.Calendar;

import static electronicbookstore.store.Status.NEW;

public class OrderArray {

    private static final int DEFAULT_CAPACITY = 10;
    private static final String ORDER_NOT_FOUND = "Такого заказа не существует!";

    private BookOrder[] array;
    private int length;

    public OrderArray() {
        this.array = new BookOrder[DEFAULT_CAPACITY];
    }

    public OrderArray(int count) {
        this.array = new BookOrder[count];
    }

    public OrderArray(BookOrder[] array) {
        this.array = array;
        this.length = array.length;
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

    public void changeOrderStatus(BookOrder bookOrder, Status status) {
        int index = searchOrderIndex(bookOrder);
        if (index >= 0) {
            array[index].setStatus(status);
        } else {
            System.out.println(ORDER_NOT_FOUND);
        }
    }

    private int searchOrderIndex(BookOrder bookOrder) {
        for (int i = 0; i < length; i++) {
            if (array[i].equals(bookOrder) && array[i].getStatus() == NEW) {
                return i;
            }
        }
        return -1;
    }

    public BookOrder get(int index) {
        if (index >= 0 && index < length) {
            return array[index];
        }
        return null;
    }

    public BookOrder[] getArray() {
        return array;
    }

    public BookOrder[] getCompletedOrder(Calendar dateFrom, Calendar dateTo) {
        BookOrder[] orders = new BookOrder[0];
        int index;

        for (BookOrder order : array) {
            if (isBelongsDateToRange(order.getOrderCompletionDate(), dateFrom, dateTo)){
                index = orders.length;
                orders = Arrays.copyOf(orders, index + 1);
                orders[index] = order;
            }
        }

        return orders;
    }

    private boolean isBelongsDateToRange(Calendar date, Calendar dateFrom, Calendar dateTo) {
        return date != null && (date.compareTo(dateFrom) != -1 && date.compareTo(dateTo) != 1);
    }


    public int size() {
        return length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

}
