package electronicbookstore.store;

import electronicbookstore.storage.Book;
import electronicbookstore.storage.Bookshelf;

import java.util.Calendar;

public interface Store {

    void addBookOnStorage(Book book);
    void addOrder(BookOrder bookOrder);
    void addRequest(BookRequest bookRequest);
    void cancelOrder(BookOrder bookOrder);
    void changeOrderStatus();
    double earnedMoney(Calendar dateFrom, Calendar dateTo);
    Bookshelf[] getBookList();
    BookOrder[] getCompletedOrderList(Calendar dateFrom, Calendar dateTo);
    int getCountCompletedOrder(Calendar dateFrom, Calendar dateTo);
    BookOrder[] getOrderList();
    BookRequest[] getRequestList(Book book);
    Bookshelf[] getUnsoldBookList();

}
