package electronicbookstore.store;

import electronicbookstore.storage.Book;
import electronicbookstore.storage.Bookshelf;

import java.util.Calendar;

public interface Store {

    void addBookOnStorage(Book book);
    void addOrder(Customer customer, Book... books);
    void addRequest(Book book);
    void cancelOrder(BookOrder bookOrder);
    void completeOrder(BookOrder bookOrder);
    double earnedMoney(Calendar dateFrom, Calendar dateTo);
    Bookshelf[] getBookList();
    BookOrder[] getCompletedOrderList(Calendar dateFrom, Calendar dateTo);
    int getCountCompletedOrder(Calendar dateFrom, Calendar dateTo);
    BookOrder[] getOrderList();
    BookRequest[] getRequestList(Book book);
    Bookshelf[] getUnsoldBookList();

}
