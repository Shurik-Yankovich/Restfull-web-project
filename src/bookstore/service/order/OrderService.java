package bookstore.service.order;

import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.book.Book;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Order addOrder(Customer customer, Book... books);
    Order addOrder(Order bookOrder);
    Order cancelOrder(Order bookOrder);
    Order completeOrder(Order bookOrder);
    double earnedMoney(LocalDate dateFrom, LocalDate dateTo);
    List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    List<Order> getOrderList();
    List<Order> getNewOrder();
    List<Order> getSortingOrderList();
    void readAllFromFile();
    void writeAllToFile();
    void writeOrderToFile(Order order);
    void updateOrderToFile(Order order);
}
