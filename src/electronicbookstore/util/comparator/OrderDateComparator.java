package electronicbookstore.util.comparator;

import electronicbookstore.model.Order;

import java.util.Comparator;

public class OrderDateComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return order1.getOrderDate().compareTo(order2.getOrderDate());
    }
}
