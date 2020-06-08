package electronicbookstore.store;

import electronicbookstore.storage.Book;
import electronicbookstore.storage.Bookshelf;
import electronicbookstore.store.arrays.Order;
import electronicbookstore.store.arrays.Request;

import java.util.Calendar;

public interface Store {

    void addBookOnStorage(Book book);
    void addOrder(Customer customer, Book... books);
    void addOrder(Order bookOrder);
    int addRequest(Book book);
    void cancelOrder(Order bookOrder);
    boolean completeOrder(Order bookOrder);
    double earnedMoney(Calendar dateFrom, Calendar dateTo);
    Bookshelf[] getBookList();
    Order[] getCompletedOrderList(Calendar dateFrom, Calendar dateTo);
    int getCountCompletedOrder(Calendar dateFrom, Calendar dateTo);
    Order[] getOrderList();
    Request[] getRequestList();
    Bookshelf[] getUnsoldBookList();

}
