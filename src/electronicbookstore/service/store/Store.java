package electronicbookstore.service.store;

import electronicbookstore.model.*;

import java.time.LocalDate;
import java.util.List;

public interface Store {

    void addBookOnStorage(Book book);
    void addOrder(Customer customer, Book... books);
    void addOrder(Order bookOrder);
    int addRequest(Book book);
    void cancelOrder(Order bookOrder);
    boolean completeOrder(Order bookOrder);
    double earnedMoney(LocalDate dateFrom, LocalDate dateTo);
    List<Bookshelf> getBookList();
    List<Bookshelf> getSortingBookList();
    List<Order> getCompletedOrderList(LocalDate dateFrom, LocalDate dateTo);
    int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    List<Order> getOrderList();
    List<Order> getSortingOrderList();
    List<Request> getRequestList();
    List<Bookshelf> getUnsoldBookList();

}
