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
        BookOrder bookOrder = new BookOrder(customer, new GregorianCalendar(), books);
        double price = getTotalPrice(books);
        bookOrder.setPrice(price);
        orderList.add(bookOrder);
    }

    private double getTotalPrice(Book[] books) {
        double price = 0;
        for (Book book : books) {
            price += bookStorage.getBookshelf(book).getPrice();
        }
        return price;
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
        BookOrder[] bookOrders = getCompletedOrderList(dateFrom, dateTo);
        double money = 0;
        for (BookOrder order : bookOrders) {
            money += getTotalPrice(order.getBooks());
        }
        return money;
    }

    @Override
    public Bookshelf[] getBookList() {
        return bookStorage.getBookshelfList();
    }

    @Override
    public BookOrder[] getCompletedOrderList(Calendar dateFrom, Calendar dateTo) {
        return orderList.getCompletedOrder(dateFrom, dateTo);
    }

    @Override
    public int getCountCompletedOrder(Calendar dateFrom, Calendar dateTo) {
        return getCompletedOrderList(dateFrom, dateTo).length;
    }

    @Override
    public BookOrder[] getOrderList() {
        return orderList.getArray();
    }

    @Override
    public BookRequest[] getRequestList() {
        return requestList.getArray();
    }

    @Override
    public Bookshelf[] getUnsoldBookList() {
        return bookStorage.getUnsoldBookshelfList();
    }
}
