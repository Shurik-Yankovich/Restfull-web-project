package bookstore.controller.action;

import bookstore.model.Bookshelf;
import bookstore.model.Customer;
import bookstore.model.Order;
import bookstore.model.book.Book;
import bookstore.service.order.OrderService;
import bookstore.service.request.RequestService;
import bookstore.service.storage.StorageService;
import bookstore.view.in.ViewIn;
import bookstore.view.out.ViewOut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        double money = orderService.earnedMoney(dateFrom, dateTo);
        viewOut.earnedMoneyOut(money);
    }

    public void exitAction() {
        System.exit(1);
    }

    public void addOrderAction() {
        orderService.addOrder(createOrder());
    }

    private Order createOrder() {
        Customer customer = viewIn.readCustomer();
        List<Book> booksInOrder = new ArrayList<>();
        List<Bookshelf> bookshelves = storageService.getBookshelfList();
        for (Integer bookNumber : viewIn.readBookList(bookshelves)) {
            booksInOrder.add(bookshelves.get(bookNumber).getBook());
        }
        return new Order(customer, booksInOrder);
    }

    public void cancelOrderAction() {
        Order order = viewIn.choiceFromList(orderService.getNewOrder());
        viewOut.cancelOrderOut(orderService.cancelOrder(order));
    }

    public void completeOrderAction() {
        Order order = viewIn.choiceFromList(orderService.getNewOrder());
        viewOut.completeOrderOut(orderService.completeOrder(order));
    }

    public void countCompletedOrderAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        int countCompletedOrder = orderService.getCountCompletedOrder(dateFrom, dateTo);
        viewOut.countCompletedOrderOut(countCompletedOrder);
    }

    public void showCompletedOrdersAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        List<Order> orders = orderService.getCompletedOrder(dateFrom, dateTo);
        viewOut.printList(orders);
    }

    public void sortingOrdersAction() {
        viewOut.printList(orderService.getSortingOrderList());
    }

    public void unsortingOrdersAction() {
        viewOut.printList(orderService.getOrderList());
    }

    public void addRequestAction() {
        Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
        requestService.addRequest(book);
    }

    public void sortingRequestsAction() {
        viewOut.printList(requestService.getSortingRequestList());
    }

    public void unsortingRequestAction() {
        viewOut.printList(requestService.getRequestList());
    }

    public void addBookAction() {
        Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
        int count = viewIn.readCountBook();
        storageService.addBookOnStorage(book, count);
    }

    public void showUnsoldBooks() {
        viewOut.printList(storageService.getUnsoldBookshelves());
    }

    public void sortingBooksAction() {
        viewOut.printList(storageService.getSortingBookshelves());
    }

    public void unsortingBooksAction() {
        viewOut.printList(storageService.getBookshelfList());
    }

    public void exportDataAction(){storageService.exportData();}

    public void importDataAction(){storageService.importData();}

    public void notFoundMenuItem() {
        viewOut.notFoundMenuItem();
    }

    public int readIntFromConsole() {
        return viewIn.readIntFromConsole();
    }
}
