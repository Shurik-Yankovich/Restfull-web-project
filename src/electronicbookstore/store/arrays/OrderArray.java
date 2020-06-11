package electronicbookstore.store.arrays;

import electronicbookstore.comparator.OrderCompletionDateComparator;
import electronicbookstore.comparator.OrderDateComparator;
import electronicbookstore.comparator.OrderPriceComparator;
import electronicbookstore.comparator.OrderStatusComparator;
import electronicbookstore.store.Status;

import java.util.*;

import static electronicbookstore.store.Status.COMPLETED;

public class OrderArray {

    private static final String ORDER_NOT_FOUND = "Order not found!";

    private List<Order> array;

    public OrderArray() {
        this.array = new ArrayList<>();
    }

    public OrderArray(int count) {
        this.array = new ArrayList<>(count);
    }

    public OrderArray(Order[] array) {
        this.array = Arrays.asList(array);
    }

    public OrderArray(List<Order> array) {
        this.array = array;
    }

    public void add(Order element) {
        array.add(element);
    }

    public void changeOrderStatus(Order bookOrder, Status status) {

        if (array.contains(bookOrder)) {
            int index = array.indexOf(bookOrder);
            Order order = array.get(index);
            order.setStatus(status);
            if (status == COMPLETED) {
                order.setOrderCompletionDate(new GregorianCalendar());
            }
        } else {
            System.out.println(ORDER_NOT_FOUND);
        }
    }

    public Order get(int index) {
        return array.get(index);
    }

    public List<Order> getArray() {
        return array;
    }

    public List<Order> getSortingArray() {
        return sortBookOrders();
    }

    public List<Order> getCompletedOrder(Calendar dateFrom, Calendar dateTo) {
        List<Order> orders = new ArrayList<>();

        for (Order order : array) {
            if (isBelongsDateToRange(order.getOrderCompletionDate(), dateFrom, dateTo)) {
                orders.add(order);
            }
        }

        if (orders.size() > 0) {
            sortCompletedOrders(orders);
        }
        return orders;
    }

    private boolean isBelongsDateToRange(Calendar date, Calendar dateFrom, Calendar dateTo) {
        return date != null && (date.compareTo(dateFrom) > 0 && date.compareTo(dateTo) < 0);
    }


    public int size() {
        return array.size();
    }

    private List<Order> sortBookOrders() {
        List<Order> orders = new ArrayList<>();
        Collections.copy(orders, array);
        Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                .thenComparing(new OrderStatusComparator());
        orders.sort(orderComp);
        return orders;
    }

    private void sortCompletedOrders(List<Order> orders) {
        Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
        orders.sort(orderComp);
    }

    private void sortCompletionDate(List<Order> orders) {
        orders.sort(new OrderCompletionDateComparator());
    }

    private void sortOrderDate(List<Order> orders) {
        orders.sort(new OrderDateComparator());
    }

    private void sortPrice(List<Order> orders) {
        orders.sort(new OrderPriceComparator());
    }

    private void sortStatus(List<Order> orders) {
        orders.sort(new OrderStatusComparator());
    }

    @Override
    public String toString() {
        return array.toString();
    }

}
