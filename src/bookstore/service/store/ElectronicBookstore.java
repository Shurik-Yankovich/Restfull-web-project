package bookstore.service.store;

import bookstore.model.*;
import bookstore.repository.order.OrderArray;
import bookstore.repository.order.OrderRepository;
import bookstore.repository.request.RequestArray;
import bookstore.repository.request.RequestRepository;
import bookstore.repository.storage.BookStorage;
import bookstore.repository.storage.Storage;
import bookstore.util.BookGenerator;
import bookstore.util.comparator.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.model.Status.CANCELED;
import static bookstore.model.Status.COMPLETED;

public class ElectronicBookstore implements Store {

    public static final ElectronicBookstore bookstore = new ElectronicBookstore(new BookStorage(BookGenerator.generate()));
    private static final String BOOK_NOT_FOUND = "Book not found!";

    private Storage bookStorage;
    private OrderRepository orderList;
    private RequestRepository requestList;

    private ElectronicBookstore(Storage bookStorage) {
        this.bookStorage = bookStorage;
        orderList = new OrderArray();
        requestList = new RequestArray();
    }

    @Override
    public void addBookOnStorage(Book book, int count) {
        requestList.closeRequest(book);
        bookStorage.comingBook(book, count);
    }

    @Override
    public void addOrder(Customer customer, Book... books) {
        Order bookOrder = new Order(customer, books);
        addOrder(bookOrder);
    }

    @Override
    public void addOrder(Order bookOrder) {
        double price = getTotalPrice(bookOrder.getBooks());
        bookOrder.setPrice(price);
        List<Integer> numbersRequest = checkBooksOnStorage(bookOrder.getBooks());
        bookOrder.setNumbersRequest(numbersRequest);
        orderList.add(bookOrder);
    }


    private double getTotalPrice(List<Book> books) {
        double price = 0;
        for (Book book : books) {
            price += bookStorage.getBookshelf(book).getPrice();
        }
        return price;
    }

    private List<Integer> checkBooksOnStorage(List<Book> books) {
        List<Integer> result = new ArrayList<>();

        for (Book book : books) {
            if (!bookStorage.takeOutBook(book)) {
                result.add(addRequest(book));
            }
        }
        return result;
    }

    @Override
    public int addRequest(Book book) {
        return requestList.add(new Request(book));
    }

    @Override
    public boolean cancelOrder(Order bookOrder) {
        for (int number : bookOrder.getNumbersRequest()) {
            requestList.changeStatus(number, CANCELED);
        }
        return orderList.changeOrderStatus(bookOrder, CANCELED);
    }

    @Override
    public boolean completeOrder(Order bookOrder) {
        boolean result = true;
        List<Integer> numbersRequest = bookOrder.getNumbersRequest();

        if (numbersRequest != null) {
            for (int number : numbersRequest) {
                if (requestList.getByRequestNumber(number).getStatus() != COMPLETED) {
                    result = false;
                }
            }
        }
        if (result) {
            result = orderList.changeOrderStatus(bookOrder, COMPLETED);
        }

        return result;
    }

    @Override
    public double earnedMoney(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> bookOrders = getCompletedOrderList(dateFrom, dateTo);
        double money = 0;
        for (Order order : bookOrders) {
            money += getTotalPrice(order.getBooks());
        }
        return money;
    }

    @Override
    public List<Bookshelf> getBookList() {
        return bookStorage.getBookshelfList();
    }

    @Override
    public List<Bookshelf> getSortingBookList() {
        List<Bookshelf> books = new ArrayList<>(bookStorage.getBookshelfList());
        if (books.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfTitleComparator().thenComparing(new BookshelfPublicationYearComparator())
                    .thenComparing(new BookshelfPriceComparator()).thenComparing(new BookshelfPresenceComparator());
            books.sort(bookComp);
        }
        return books;
    }

    @Override
    public List<Order> getCompletedOrderList(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> orders = orderList.getCompletedOrder(dateFrom, dateTo);
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
            orders.sort(orderComp);
        }
        return orders;
    }

    @Override
    public int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        return getCompletedOrderList(dateFrom, dateTo).size();
    }

    @Override
    public List<Order> getOrderList() {
        return orderList.getAll();
    }

    @Override
    public List<Order> getSortingOrderList() {
        List<Order> orders = new ArrayList<>(orderList.getAll());
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                    .thenComparing(new OrderStatusComparator());
            orders.sort(orderComp);
        }
        return orders;
    }

    @Override
    public List<Request> getRequestList() {
        return requestList.getAll();
    }

    public List<Request> getSortingRequestList() {
        List<Request> requests = new ArrayList<>(requestList.getAll());
        if (requests.size() > 0) {
            Comparator<Request> requestComp = new RequestCountComparator().thenComparing(new RequestBookNameComparator());
            requests.sort(requestComp);
        }
        return requests;
    }

    @Override
    public List<Bookshelf> getUnsoldBookList() {
        List<Bookshelf> books = bookStorage.getUnsoldBookshelfList();
        if (books.size() > 0) {
            Comparator<Bookshelf> bookComp = new BookshelfArrivalDateComparator().thenComparing(new BookshelfPriceComparator());
            books.sort(bookComp);
        }
        return books;
    }
}
