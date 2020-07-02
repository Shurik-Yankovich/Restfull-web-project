package bookstore.controller.action;

import bookstore.model.Bookshelf;
import bookstore.model.Customer;
import bookstore.model.Order;
import bookstore.model.book.Book;
import bookstore.service.order.OrderService;
import bookstore.service.request.RequestService;
import bookstore.service.storage.StorageService;
import bookstore.view.ViewIn;
import bookstore.view.ViewOut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static bookstore.service.order.BookOrderService.ORDER_SERVICE;
import static bookstore.service.request.BookRequestService.REQUEST_SERVICE;
import static bookstore.service.storage.BookStorageService.STORAGE_SERVICE;

public class StoreAction {

    private OrderService orderService;
    private RequestService requestService;
    private StorageService storageService;
    private ViewIn viewIn;
    private ViewOut viewOut;

    public StoreAction(OrderService orderService, RequestService requestService, StorageService storageService,
                       ViewIn viewIn, ViewOut viewOut) {
        this.orderService = orderService;
        this.requestService = requestService;
        this.storageService = storageService;
        this.viewIn = viewIn;
        this.viewOut = viewOut;
    }

    public void earnedMoneyAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        double money = ORDER_SERVICE.earnedMoney(dateFrom, dateTo);
        viewOut.earnedMoneyOut(money);
    }

    public void exitAction() {
        System.exit(1);
    }

    public void addOrderAction() {
        ORDER_SERVICE.addOrder(createOrder());
    }

    private Order createOrder() {
        Customer customer = viewIn.readCustomer();
        List<Book> booksInOrder = new ArrayList<>();
        List<Bookshelf> bookshelves = STORAGE_SERVICE.getBookshelfList();
        for (Integer bookNumber : viewIn.readBookList(bookshelves)) {
            booksInOrder.add(bookshelves.get(bookNumber).getBook());
        }
        return new Order(customer, booksInOrder);
    }

    public void cancelOrderAction() {
        Order order = viewIn.choiceFromList(ORDER_SERVICE.getNewOrder());
        viewOut.cancelOrderOut(ORDER_SERVICE.cancelOrder(order));
    }

    public void completeOrderAction() {
        Order order = viewIn.choiceFromList(ORDER_SERVICE.getNewOrder());
        viewOut.completeOrderOut(ORDER_SERVICE.completeOrder(order));
    }

    public void countCompletedOrderAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        int countCompletedOrder = ORDER_SERVICE.getCountCompletedOrder(dateFrom, dateTo);
        viewOut.countCompletedOrderOut(countCompletedOrder);
    }

    public void showCompletedOrdersAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        List<Order> orders = ORDER_SERVICE.getCompletedOrder(dateFrom, dateTo);
        viewOut.printList(orders);
    }

    public void sortingOrdersAction() {
        viewOut.printList(ORDER_SERVICE.getSortingOrderList());
    }

    public void unsortingOrdersAction() {
        viewOut.printList(ORDER_SERVICE.getOrderList());
    }

    public void addRequestAction() {
        Book book = viewIn.choiceFromList(STORAGE_SERVICE.getBookshelfList()).getBook();
        REQUEST_SERVICE.addRequest(book);
    }

    public void sortingRequestsAction() {
        viewOut.printList(REQUEST_SERVICE.getSortingRequestList());
    }

    public void unsortingRequestAction() {
        viewOut.printList(REQUEST_SERVICE.getRequestList());
    }

    public void addBookAction() {
        Book book = viewIn.choiceFromList(STORAGE_SERVICE.getBookshelfList()).getBook();
        int count = viewIn.readCountBook();
        STORAGE_SERVICE.addBookOnStorage(book, count);
    }

    public void showUnsoldBooks() {
        viewOut.printList(STORAGE_SERVICE.getUnsoldBookshelves());
    }

    public void sortingBooksAction() {
        viewOut.printList(STORAGE_SERVICE.getSortingBookshelves());
    }

    public void unsortingBooksAction() {
        viewOut.printList(STORAGE_SERVICE.getBookshelfList());
    }

    public void notFoundMenuItem() {
        viewOut.notFoundMenuItem();
    }

    public int readIntFromConsole() {
        return viewIn.readIntFromConsole();
    }
}
