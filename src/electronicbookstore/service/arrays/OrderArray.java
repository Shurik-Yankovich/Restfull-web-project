package electronicbookstore.service.arrays;

import electronicbookstore.util.comparator.OrderCompletionDateComparator;
import electronicbookstore.util.comparator.OrderDateComparator;
import electronicbookstore.util.comparator.OrderPriceComparator;
import electronicbookstore.util.comparator.OrderStatusComparator;
import electronicbookstore.model.Order;
import electronicbookstore.model.Status;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

import static electronicbookstore.model.Status.COMPLETED;
import static electronicbookstore.model.Status.NEW;

public class OrderArray {

    private static final String ORDER_NOT_FOUND = "Order not found!";

    private Order[] array;
    private int length;

    public OrderArray() {
        this.array = new Order[0];
    }

    public OrderArray(int count) {
        this.array = new Order[count];
    }

    public OrderArray(Order[] array) {
        this.array = array;
        this.length = array.length;
    }

    public void add(Order element) {
        if (length == array.length) {
            increaseArrayLength();
        }
        array[length] = element;
        length++;
    }

    private void increaseArrayLength() {
        array = Arrays.copyOf(array, length + 1);
    }

    public void changeOrderStatus(Order bookOrder, Status status) {
        int index = searchOrderIndex(bookOrder);
        if (index >= 0) {
            array[index].setStatus(status);
            if (status == COMPLETED) {
                array[index].setOrderCompletionDate(new GregorianCalendar());
            }
        } else {
            System.out.println(ORDER_NOT_FOUND);
        }
    }

    private int searchOrderIndex(Order bookOrder) {
        for (int i = 0; i < length; i++) {
            if (array[i].equals(bookOrder) && array[i].getStatus() == NEW) {
                return i;
            }
        }
        return -1;
    }

    public Order get(int index) {
        if (index >= 0 && index < length) {
            return array[index];
        }
        return null;
    }

    public Order[] getArray() {
        return array;
    }

    public Order[] getSortingArray() {
        return sortBookOrders();
    }

    public Order[] getCompletedOrder(Calendar dateFrom, Calendar dateTo) {
        Order[] orders = new Order[0];
        int index;

        for (Order order : array) {
            if (isBelongsDateToRange(order.getOrderCompletionDate(), dateFrom, dateTo)) {
                index = orders.length;
                orders = Arrays.copyOf(orders, index + 1);
                orders[index] = order;
            }
        }

        if (orders.length > 0) {
            sortCompletedOrders(orders);
        }
        return orders;
    }

    private boolean isBelongsDateToRange(Calendar date, Calendar dateFrom, Calendar dateTo) {
        return date != null && (date.compareTo(dateFrom) > 0 && date.compareTo(dateTo) < 0);
    }


    public int size() {
        return length;
    }

    private Order[] sortBookOrders() {
        Order[] orders =  Arrays.copyOf(array, length);
        Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                .thenComparing(new OrderStatusComparator());
        Arrays.sort(orders, orderComp);
        return orders;
    }

    private void sortCompletedOrders(Order[] orders) {
        Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
        Arrays.sort(orders, orderComp);
    }

    private void sortCompletionDate(Order[] orders) {
        Arrays.sort(orders, new OrderCompletionDateComparator());
    }

    private void sortOrderDate(Order[] orders) {
        Arrays.sort(orders, new OrderDateComparator());
    }

    private void sortPrice(Order[] orders) {
        Arrays.sort(orders, new OrderPriceComparator());
    }

    private void sortStatus(Order[] orders) {
        Arrays.sort(orders, new OrderStatusComparator());
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

}
