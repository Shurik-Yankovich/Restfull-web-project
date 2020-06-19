package bookstore.service.store;

import bookstore.model.*;
import bookstore.repository.storage.ListStorageRepository;
import bookstore.repository.storage.StorageRepository;
import bookstore.service.order.BookOrderService;
import bookstore.service.order.OrderService;
import bookstore.service.request.BookRequestService;
import bookstore.service.request.RequestService;
import bookstore.service.storage.BookStorageService;
import bookstore.service.storage.StorageService;
import bookstore.util.BookGenerator;

import java.time.LocalDate;
import java.util.List;

public class BookStoreService implements StoreService {

    public static final BookStoreService bookstore = new BookStoreService(new ListStorageRepository(BookGenerator.generate()));

    private StorageService storageService;
    private OrderService orderService;
    private RequestService requestService;

    private BookStoreService(StorageRepository bookStorageRepository) {
        storageService = new BookStorageService(bookStorageRepository);
        orderService = new BookOrderService();
        requestService = new BookRequestService();
    }

    @Override
    public void addBookOnStorage(Book book, int count) {
        requestService.completeRequest(book);
        storageService.addBookOnStorage(book, count);
    }

    @Override
    public void addOrder(Customer customer, Book... books) {
        Order bookOrder = new Order(customer, books);
        addOrder(bookOrder);
    }

    @Override
    public void addOrder(Order bookOrder) {
        double price = storageService.getTotalPrice(bookOrder.getBooks());
        bookOrder.setPrice(price);
        List<Book> books = storageService.checkBooksNotOnStorage(bookOrder.getBooks());
        List<Integer> numbersRequest = requestService.addRequestList(books);
        bookOrder.setNumbersRequest(numbersRequest);
        orderService.addOrder(bookOrder);
    }

    @Override
    public int addRequest(Book book) {
        return requestService.addRequest(book);
    }

    @Override
    public boolean cancelOrder(Order bookOrder) {
        for (int number : bookOrder.getNumbersRequest()) {
            requestService.cancelRequest(number);
        }
        return orderService.cancelOrder(bookOrder);
    }

    @Override
    public boolean completeOrder(Order bookOrder) {
        List<Integer> requestNumbers = bookOrder.getNumbersRequest();
        boolean result = requestService.checkCompleteRequest(requestNumbers);
        if (result) {
            result = orderService.completeOrder(bookOrder);
        }
        return result;
    }

    @Override
    public double earnedMoney(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> bookOrders = getCompletedOrderList(dateFrom, dateTo);
        double money = 0;
        for (Order order : bookOrders) {
            money += storageService.getTotalPrice(order.getBooks());
        }
        return money;
    }

    @Override
    public List<Bookshelf> getBookList() {
        return storageService.getAll();
    }

    @Override
    public List<Bookshelf> getSortingBookList() {
        return storageService.getSortingBookshelves();
    }

    @Override
    public List<Order> getCompletedOrderList(LocalDate dateFrom, LocalDate dateTo) {
        return orderService.getCompletedOrder(dateFrom, dateTo);
    }

    @Override
    public int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        return orderService.getCountCompletedOrder(dateFrom, dateTo);
    }

    @Override
    public List<Order> getOrderList() {
        return orderService.getAll();
    }

    @Override
    public List<Order> getSortingOrderList() {
        return orderService.getSortingOrders();
    }

    @Override
    public List<Request> getRequestList() {
        return requestService.getAll();
    }

    @Override
    public List<Request> getSortingRequestList() {
        return requestService.getSortingRequests();
    }

    @Override
    public List<Bookshelf> getUnsoldBookList() {
        return storageService.getUnsoldBookshelves();
    }
}
