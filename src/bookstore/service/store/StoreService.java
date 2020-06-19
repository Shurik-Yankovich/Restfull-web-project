package bookstore.service.store;

import bookstore.model.*;
import bookstore.model.book.Book;

import java.time.LocalDate;
import java.util.List;

public interface StoreService {

    void addBookOnStorage(Book book, int count);
    void addOrder(Customer customer, Book... books);
    void addOrder(Order bookOrder);
    int addRequest(Book book);
    boolean cancelOrder(Order bookOrder);
    boolean completeOrder(Order bookOrder);
    double earnedMoney(LocalDate dateFrom, LocalDate dateTo);
    List<Bookshelf> getBookList();
    List<Bookshelf> getSortingBookList();
    List<Order> getCompletedOrderList(LocalDate dateFrom, LocalDate dateTo);
    int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    List<Order> getOrderList();
    List<Order> getSortingOrderList();
    List<Request> getRequestList();
    List<Request> getSortingRequestList();
    List<Bookshelf> getUnsoldBookList();

}
