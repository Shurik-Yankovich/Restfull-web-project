package bookstore.controller.action;

import bookstore.entity.Bookshelf;
import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.entity.book.Book;
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
        Order order = orderService.addOrder(createOrder());
        if (saveChanging()) {
            orderService.writeOrderToFile(order);
            storageService.writeAllToFile();
            requestService.writeAllToFile();
        }
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
        order = orderService.cancelOrder(order);
        boolean isCanceled = order != null;
        viewOut.cancelOrderOut(isCanceled);
        if (isCanceled && saveChanging()) {
            orderService.updateOrderToFile(order);
            Request request;
            for (int number : order.getNumbersRequest()) {
                request = requestService.getRequestByNumber(number);
                requestService.updateRequestToFile(request);
            }
            storageService.writeAllToFile();
        }
    }

    public void completeOrderAction() {
        Order order = viewIn.choiceFromList(orderService.getNewOrder());
        order = orderService.completeOrder(order);
        boolean isCompleted = order != null;
        viewOut.completeOrderOut(isCompleted);
        if (isCompleted && saveChanging()) {
            orderService.updateOrderToFile(order);
            storageService.writeAllToFile();
        }
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

    public void exportOrderAction() {
        orderService.writeAllToFile();
    }

    public void importOrderAction() {
        orderService.readAllFromFile();
    }

    public void addRequestAction() {
        Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
        requestService.addRequest(book);
        if (saveChanging()) {
            requestService.writeAllToFile();
        }
    }

    public void sortingRequestsAction() {
        viewOut.printList(requestService.getSortingRequestList());
    }

    public void unsortingRequestAction() {
        viewOut.printList(requestService.getRequestList());
    }

    public void exportRequestAction() {
        requestService.writeAllToFile();
    }

    public void importRequestAction() {
        requestService.readAllFromFile();
    }

    public void addBookAction() {
        Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
        int count = viewIn.readCountBook();
        Bookshelf bookshelf = storageService.addBookOnStorage(book, count);
        if (saveChanging()) {
            storageService.updateBookshelfToFile(bookshelf);
            requestService.writeAllToFile();
        }
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

    public void exportBookshelfAction() {
        storageService.writeAllToFile();
    }

    public void importBookshelfAction() {
        storageService.readAllFromFile();
    }

    public void notFoundMenuItem() {
        viewOut.notFoundMenuItem();
    }

    public int readIntFromConsole() {
        return viewIn.readIntFromConsole();
    }

    private boolean saveChanging() {
        int choice = viewIn.saveChanging();
        return choice == 0;
    }
}
