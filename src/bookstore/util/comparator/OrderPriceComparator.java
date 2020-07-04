package bookstore.util.comparator;

import bookstore.entity.Order;

import java.util.Comparator;

public class OrderPriceComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return Double.compare(order1.getPrice(), order2.getPrice());
    }
}
