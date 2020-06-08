package electronicbookstore.store;

import electronicbookstore.storage.Book;
import electronicbookstore.storage.Bookshelf;
import electronicbookstore.store.arrays.BookOrder;
import electronicbookstore.store.arrays.BookRequest;

import java.util.Calendar;

public interface Store {

    void addBookOnStorage(Book book);
    void addOrder(Customer customer, Book... books);
    void addOrder(BookOrder bookOrder);
    int addRequest(Book book);
    void cancelOrder(BookOrder bookOrder);
    boolean completeOrder(BookOrder bookOrder);
    double earnedMoney(Calendar dateFrom, Calendar dateTo);
    Bookshelf[] getBookList();
    BookOrder[] getCompletedOrderList(Calendar dateFrom, Calendar dateTo);
    int getCountCompletedOrder(Calendar dateFrom, Calendar dateTo);
    BookOrder[] getOrderList();
    BookRequest[] getRequestList();
    Bookshelf[] getUnsoldBookList();

}
