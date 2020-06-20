package bookstore.service.order;

import bookstore.model.Customer;
import bookstore.model.Order;
import bookstore.model.book.Book;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void addOrder(Customer customer, Book... books);
    void addOrder(Order bookOrder);
    boolean cancelOrder(Order bookOrder);
    boolean completeOrder(Order bookOrder);
    double earnedMoney(LocalDate dateFrom, LocalDate dateTo);
    List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    List<Order> getOrderList();
    List<Order> getNewOrder();
    List<Order> getSortingOrderList();
}
