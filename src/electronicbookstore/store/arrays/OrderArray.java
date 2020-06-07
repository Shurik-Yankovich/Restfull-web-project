package electronicbookstore.store.arrays;

import electronicbookstore.comparator.OrderCompletionDateComparator;
import electronicbookstore.comparator.OrderDateComparator;
import electronicbookstore.comparator.OrderPriceComparator;
import electronicbookstore.comparator.OrderStatusComparator;
import electronicbookstore.store.BookOrder;
import electronicbookstore.store.Status;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

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
        return sortBookOrders();
    }

    public BookOrder[] getCompletedOrder(Calendar dateFrom, Calendar dateTo) {
        BookOrder[] orders = new BookOrder[0];
        int index;

        for (BookOrder order : array) {
            if (isBelongsDateToRange(order.getOrderCompletionDate(), dateFrom, dateTo)) {
                index = orders.length;
                orders = Arrays.copyOf(orders, index + 1);
                orders[index] = order;
            }
        }

        sortCompletedOrders(orders);
        return orders;
    }

    private boolean isBelongsDateToRange(Calendar date, Calendar dateFrom, Calendar dateTo) {
        return date != null && (date.compareTo(dateFrom) != -1 && date.compareTo(dateTo) != 1);
    }


    public int size() {
        return length;
    }

    private BookOrder[] sortBookOrders() {
        BookOrder[] orders = array;
        Comparator<BookOrder> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                .thenComparing(new OrderStatusComparator());
        Arrays.sort(orders, orderComp);
        return orders;
    }

    private void sortCompletedOrders(BookOrder[] orders) {
        Comparator<BookOrder> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
        Arrays.sort(orders, orderComp);
    }

    private void sortCompletionDate(BookOrder[] orders) {
        Arrays.sort(orders, new OrderCompletionDateComparator());
    }

    private void sortOrderDate(BookOrder[] orders){
        Arrays.sort(orders, new OrderDateComparator());
    }

    private void sortPrice(BookOrder[] orders) {
        Arrays.sort(orders, new OrderPriceComparator());
    }

    private void sortStatus(BookOrder[] orders) {
        Arrays.sort(orders, new OrderStatusComparator());
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

}
