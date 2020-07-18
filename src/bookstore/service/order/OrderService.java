package bookstore.service.order;

import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Order addOrder(Customer customer, Book... books) throws RepositoryException;

    Order addOrder(Order bookOrder) throws RepositoryException;

    Order cancelOrder(Order bookOrder) throws RepositoryException;

    Order completeOrder(Order bookOrder) throws RepositoryException;

    double earnedMoney(LocalDate dateFrom, LocalDate dateTo) throws RepositoryException;

    List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo) throws RepositoryException;

    int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo) throws RepositoryException;

    List<Order> getOrderList() throws RepositoryException;

    List<Order> getNewOrder() throws RepositoryException;

    List<Order> getSortingOrderList() throws RepositoryException;

    void readAllFromFile() throws RepositoryException;

    void writeAllToFile() throws RepositoryException;

    void writeOrderToFile(Order order) throws RepositoryException;

    void updateOrderToFile(Order order) throws RepositoryException;
}
