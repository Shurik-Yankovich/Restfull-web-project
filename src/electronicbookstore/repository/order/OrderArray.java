package electronicbookstore.repository.order;

import electronicbookstore.model.Order;
import electronicbookstore.model.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static electronicbookstore.model.Status.COMPLETED;

public class OrderArray implements OrderRepository {

    private static final String ORDER_NOT_FOUND = "Order not found!";

    private List<Order> array;

    public OrderArray() {
        this.array = new ArrayList<>();
    }

    public OrderArray(int count) {
        this.array = new ArrayList<>(count);
    }

    public OrderArray(Order... array) {
        this.array = Arrays.asList(array);
    }

    public OrderArray(List<Order> array) {
        this.array = array;
    }

    @Override
    public void add(Order element) {
        array.add(element);
    }

    @Override
    public void changeOrderStatus(Order bookOrder, Status status) {

        if (array.contains(bookOrder)) {
            int index = array.indexOf(bookOrder);
            Order order = array.get(index);
            order.setStatus(status);
            if (status == COMPLETED) {
                order.setOrderCompletionDate(LocalDate.now());
            }
        } else {
            System.out.println(ORDER_NOT_FOUND);
        }
    }

    @Override
    public Order get(int index) {
        return array.get(index);
    }

    @Override
    public List<Order> getArray() {
        return array;
    }

    @Override
    public List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> orders = new ArrayList<>();

        for (Order order : array) {
            if (isBelongsDateToRange(order.getOrderCompletionDate(), dateFrom, dateTo)) {
                orders.add(order);
            }
        }
        return orders;
    }

    private boolean isBelongsDateToRange(LocalDate date, LocalDate dateFrom, LocalDate dateTo) {
        return date != null && (date.isAfter(dateFrom) && date.isBefore(dateTo));
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public String toString() {
        return array.toString();
    }

}
