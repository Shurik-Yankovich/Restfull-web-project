package service.order;

import entity.Customer;
import entity.Order;
import entity.Book;

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

    Order getOrderById(Integer id);

    List<Order> getOrderList();

    List<Order> getNewOrders();

    List<Order> getSortingOrderList();

    boolean readAllFromFile();

    boolean writeAllToFile();

    boolean writeOrderToFile(Order order);

    boolean updateOrderToFile(Order order);
}
