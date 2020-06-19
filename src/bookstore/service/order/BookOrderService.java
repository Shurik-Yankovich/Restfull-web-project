package bookstore.service.order;

import bookstore.model.Order;
import bookstore.repository.order.OrderRepository;
import bookstore.util.comparator.OrderCompletionDateComparator;
import bookstore.util.comparator.OrderDateComparator;
import bookstore.util.comparator.OrderPriceComparator;
import bookstore.util.comparator.OrderStatusComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.model.Status.CANCELED;
import static bookstore.model.Status.COMPLETED;

public class BookOrderService implements OrderService {

    private OrderRepository orderList;

    @Override
    public void addOrder(Order bookOrder) {
        orderList.add(bookOrder);
    }

    @Override
    public boolean cancelOrder(Order bookOrder) {
        return orderList.changeOrderStatus(bookOrder, CANCELED);
    }

    @Override
    public boolean completeOrder(Order bookOrder) {
        return orderList.changeOrderStatus(bookOrder, COMPLETED);
    }

    @Override
    public List<Order> getCompletedOrderList(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> orders = orderList.getCompletedOrder(dateFrom, dateTo);
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
            orders.sort(orderComp);
        }
        return orders;
    }

    @Override
    public int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        return getCompletedOrderList(dateFrom, dateTo).size();
    }

    @Override
    public List<Order> getOrderList() {
        return orderList.getAll();
    }

    @Override
    public List<Order> getSortingOrderList() {
        List<Order> orders = new ArrayList<>(orderList.getAll());
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                    .thenComparing(new OrderStatusComparator());
            orders.sort(orderComp);
        }
        return orders;
    }
}
