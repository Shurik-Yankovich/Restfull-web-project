package electronicbookstore.service.store;

import electronicbookstore.model.*;
import electronicbookstore.repository.order.OrderArray;
import electronicbookstore.repository.order.OrderRepository;
import electronicbookstore.repository.request.RequestArray;
import electronicbookstore.repository.request.RequestRepository;
import electronicbookstore.repository.storage.Storage;
import electronicbookstore.util.comparator.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import static electronicbookstore.model.Status.CANCELED;
import static electronicbookstore.model.Status.COMPLETED;

public class ElectronicBookstore implements Store {

    private static final String BOOK_NOT_FOUND = "Book not found!";

    private Storage bookStorage;
    private OrderRepository orderList;
    private RequestRepository requestList;

    public ElectronicBookstore(Storage bookStorage) {
        this.bookStorage = bookStorage;
        orderList = new OrderArray();
        requestList = new RequestArray();
    }

    @Override
    public void addBookOnStorage(Book book) {
        requestList.closeRequest(book);
        bookStorage.comingBook(book);
    }

    @Override
    public void addOrder(Customer customer, Book... books) {
        Order bookOrder = new Order(customer, books);
        double price = getTotalPrice(books);
        bookOrder.setPrice(price);
        int[] numbersRequest = checkBooksOnStorage(books);
        bookOrder.setNumbersRequest(numbersRequest);
        orderList.add(bookOrder);
    }

    @Override
    public void addOrder(Order bookOrder) {
        double price = getTotalPrice(bookOrder.getBooks());
        bookOrder.setPrice(price);
        int[] numbersRequest = checkBooksOnStorage(bookOrder.getBooks());
        bookOrder.setNumbersRequest(numbersRequest);
        orderList.add(bookOrder);
    }


    private double getTotalPrice(Book[] books) {
        double price = 0;
        for (Book book : books) {
            price += bookStorage.getBookshelf(book).getPrice();
        }
        return price;
    }

    private int[] checkBooksOnStorage(Book[] books) {
        int[] result = new int[0];
        int index;
        Bookshelf bookshelf;

        for (Book book : books) {
            bookshelf = bookStorage.getBookshelf(book);
            if (bookshelf == null) {
                System.out.println(BOOK_NOT_FOUND);
            } else if (!bookshelf.isPresence()) {
                index = result.length;
                result = Arrays.copyOf(result, index + 1);
                result[index] = addRequest(book);
            } else {
                bookshelf.setPresence(false);
            }
        }

        return result;
    }

    @Override
    public int addRequest(Book book) {
        return requestList.add(new Request(book));
    }

    @Override
    public void cancelOrder(Order bookOrder) {
        for (int number : bookOrder.getNumbersRequest()) {
            requestList.changeStatus(number, CANCELED);
        }
        orderList.changeOrderStatus(bookOrder, CANCELED);
    }

    @Override
    public boolean completeOrder(Order bookOrder) {
        boolean result = true;
        int[] numbersRequest = bookOrder.getNumbersRequest();

        if (numbersRequest != null) {
            for (int number : numbersRequest) {
                if (requestList.getByRequestNumber(number).getStatus() != COMPLETED) {
                    result = false;
                }
            }
        }
        if (result) {
            orderList.changeOrderStatus(bookOrder, COMPLETED);
        }

        return result;
    }

    @Override
    public double earnedMoney(Calendar dateFrom, Calendar dateTo) {
        Order[] bookOrders = getCompletedOrderList(dateFrom, dateTo);
        double money = 0;
        for (Order order : bookOrders) {
            money += getTotalPrice(order.getBooks());
        }
        return money;
    }

    @Override
    public Bookshelf[] getBookList() {
        Bookshelf[] books = bookStorage.getBookshelfList();
        if (books.length > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                    .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
            Arrays.sort(books, bookComp);
        }
        return books;
    }

    @Override
    public Order[] getCompletedOrderList(Calendar dateFrom, Calendar dateTo) {
        Order[] orders = orderList.getCompletedOrder(dateFrom, dateTo);
        if (orders.length > 0) {
            Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
            Arrays.sort(orders, orderComp);
        }
        return orders;
    }

    @Override
    public int getCountCompletedOrder(Calendar dateFrom, Calendar dateTo) {
        return getCompletedOrderList(dateFrom, dateTo).length;
    }

    @Override
    public Order[] getOrderList() {
        Order[] orders = orderList.getArray();
        if (orders.length > 0) {
            Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                    .thenComparing(new OrderStatusComparator());
            Arrays.sort(orders, orderComp);
        }
        return orders;
    }

    @Override
    public Request[] getRequestList() {
        Request[] requests = requestList.getArray();
        if (requests.length > 0) {
            Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
            Arrays.sort(requests, requestComp);
        }
        return requests;
    }

    @Override
    public Bookshelf[] getUnsoldBookList() {
        Bookshelf[] books = bookStorage.getUnsoldBookshelfList();
        if (books.length > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
            Arrays.sort(books, bookComp);
        }
        return books;
    }
}
