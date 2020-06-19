package bookstore.repository.order;

import bookstore.model.Order;
import bookstore.model.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bookstore.model.Status.COMPLETED;

public class ListOrderRepository implements OrderRepository {

    private static final String ORDER_NOT_FOUND = "Order not found!";

    private List<Order> array;

    public ListOrderRepository() {
        this.array = new ArrayList<>();
    }

    public ListOrderRepository(Order... array) {
        this.array = Arrays.asList(array);
    }

    public ListOrderRepository(List<Order> array) {
        this.array = array;
    }

    @Override
    public void add(Order element) {
        array.add(element);
    }

    @Override
    public boolean changeStatus(Order bookOrder, Status status) {

        if (array.contains(bookOrder)) {
            int index = array.indexOf(bookOrder);
            Order order = array.get(index);
            order.setStatus(status);
            if (status == COMPLETED) {
                order.setOrderCompletionDate(LocalDate.now());
            }
            return true;
        } else {
            System.out.println(ORDER_NOT_FOUND);
        }
        return false;
    }

    @Override
    public Order get(int index) {
        return array.get(index);
    }

    @Override
    public List<Order> getAll() {
        return array;
    }

    @Override
    public List<Order> getCompletedOrders(LocalDate dateFrom, LocalDate dateTo) {
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
