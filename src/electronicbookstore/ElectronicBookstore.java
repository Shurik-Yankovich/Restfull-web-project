package electronicbookstore;

import electronicbookstore.arraylist.OrderArrayList;

import java.util.Calendar;

public class ElectronicBookstore implements Store{

    BookStorage bookStorage;
    OrderArrayList orderList;
    OrderArrayList requestList;

    public ElectronicBookstore() {
        bookStorage = new BookStorage();
        orderList = new OrderArrayList();
        requestList = new OrderArrayList();
    }

    @Override
    public void addBookOnStorage(Book book) {

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
