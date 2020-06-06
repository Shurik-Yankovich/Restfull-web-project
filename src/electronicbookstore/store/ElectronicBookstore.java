package electronicbookstore.store;

import electronicbookstore.arraylist.MyArrayList;
import electronicbookstore.arraylist.OrderArrayList;
import electronicbookstore.arraylist.RequestArrayList;
import electronicbookstore.storage.Book;
import electronicbookstore.storage.BookStorage;
import electronicbookstore.storage.Bookshelf;


import java.util.Calendar;

public class ElectronicBookstore implements Store{

    BookStorage bookStorage;
    MyArrayList orderList;
    MyArrayList requestList;

    public ElectronicBookstore() {
        bookStorage = new BookStorage();
        orderList = new OrderArrayList();
        requestList = new RequestArrayList();
    }

    @Override
    public void addBookOnStorage(Book book) {
        bookStorage.changePresence(book, true);
    }

    @Override
    public void addOrder(BookOrder bookOrder) {

    }

    @Override
    public void addRequest(BookRequest bookRequest) {

    }

    @Override
    public void cancelOrder(BookOrder bookOrder) {

    }

    @Override
    public void changeOrderStatus() {

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
