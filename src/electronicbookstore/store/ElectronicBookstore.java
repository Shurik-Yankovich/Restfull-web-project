package electronicbookstore.store;

import electronicbookstore.storage.Book;
import electronicbookstore.storage.BookStorage;
import electronicbookstore.storage.Bookshelf;
import electronicbookstore.store.arrays.OrderArray;
import electronicbookstore.store.arrays.RequestArray;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static electronicbookstore.store.Status.*;

public class ElectronicBookstore implements Store {

    BookStorage bookStorage;
    OrderArray orderList;
    RequestArray requestList;

    public ElectronicBookstore() {
        bookStorage = new BookStorage();
        orderList = new OrderArray();
        requestList = new RequestArray();
    }

    @Override
    public void addBookOnStorage(Book book) {
        requestList.closeRequest(book);
        bookStorage.changePresence(book, true);
    }

    @Override
    public void addOrder(Customer customer, Book... books) {
        orderList.add(new BookOrder(customer, new GregorianCalendar(), books));
    }

    @Override
    public void addRequest(Book book) {
        requestList.add(new BookRequest(book));
    }

    @Override
    public void cancelOrder(BookOrder bookOrder) {
        orderList.changeOrderStatus(bookOrder, CANCELED);
    }

    @Override
    public void completeOrder(BookOrder bookOrder) {
        orderList.changeOrderStatus(bookOrder, COMPLETED);
    }

    @Override
    public double earnedMoney(Calendar dateFrom, Calendar dateTo) {
        return 0;
    }

    @Override
    public Bookshelf[] getBookList() {
        return new Bookshelf[0];
    }

    @Override
    public BookOrder[] getCompletedOrderList(Calendar dateFrom, Calendar dateTo) {
        return new BookOrder[0];
    }

    @Override
    public int getCountCompletedOrder(Calendar dateFrom, Calendar dateTo) {
        return 0;
    }

    @Override
    public BookOrder[] getOrderList() {
        return new BookOrder[0];
    }

    @Override
    public BookRequest[] getRequestList(Book book) {
        return new BookRequest[0];
    }

    @Override
    public Bookshelf[] getUnsoldBookList() {
        return new Bookshelf[0];
    }
}
