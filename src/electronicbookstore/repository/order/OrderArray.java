package electronicbookstore.repository.order;

import electronicbookstore.model.Order;
import electronicbookstore.model.Status;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static electronicbookstore.model.Status.COMPLETED;
import static electronicbookstore.model.Status.NEW;

public class OrderArray implements OrderRepository{

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

    @Override
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

    @Override
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

    @Override
    public Order get(int index) {
        if (index >= 0 && index < length) {
            return array[index];
        }
        return null;
    }

    @Override
    public Order[] getArray() {
        return array;
    }

    @Override
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
        return orders;
    }

    private boolean isBelongsDateToRange(Calendar date, Calendar dateFrom, Calendar dateTo) {
        return date != null && (date.compareTo(dateFrom) > 0 && date.compareTo(dateTo) < 0);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

}
