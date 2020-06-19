package bookstore.service.order;

import bookstore.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void addOrder(Order bookOrder);
    boolean cancelOrder(Order bookOrder);
    boolean completeOrder(Order bookOrder);
    List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    List<Order> getAll();
    List<Order> getSortingOrders();
}
