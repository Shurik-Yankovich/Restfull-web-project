package bookstore.controller.action;

import bookstore.entity.Bookshelf;
import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;
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
        try {
            LocalDate dateFrom = viewIn.readDateFrom();
            LocalDate dateTo = viewIn.readDateTo();
            double money = orderService.earnedMoney(dateFrom, dateTo);
            viewOut.earnedMoneyOut(money);
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }

    }

    public void exitAction() {
        System.exit(1);
    }

    public void addOrderAction() {
        try {
            Order order = orderService.addOrder(createOrder());
            if (saveChanging()) {
                orderService.writeOrderToFile(order);
                storageService.writeAllToFile();
                requestService.writeAllToFile();
            }
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    private Order createOrder() throws RepositoryException {
        Customer customer = viewIn.readCustomer();
        List<Book> booksInOrder = new ArrayList<>();
        List<Bookshelf> bookshelves = storageService.getBookshelfList();
        for (Integer bookNumber : viewIn.readBookList(bookshelves)) {
            booksInOrder.add(bookshelves.get(bookNumber).getBook());
        }
        return new Order(customer, booksInOrder);
    }

    public void cancelOrderAction() {
        try {
            Order order = viewIn.choiceFromList(orderService.getNewOrder());
            Order cancelOrder = orderService.cancelOrder(order);
            boolean isCanceled = cancelOrder != null;
            viewOut.cancelOrderOut(isCanceled);
            if (isCanceled && saveChanging()) {
                orderService.updateOrderToFile(cancelOrder);
                for (int number : cancelOrder.getNumbersRequest()) {
                    Request request = requestService.getRequestByNumber(number);
                    requestService.updateRequestToFile(request);
                }
                storageService.writeAllToFile();
            }
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }

    }

    public void completeOrderAction() {
        try {
            Order order = viewIn.choiceFromList(orderService.getNewOrder());
            Order completeOrder = orderService.completeOrder(order);
            boolean isCompleted = completeOrder != null;
            viewOut.completeOrderOut(isCompleted);
            if (isCompleted && saveChanging()) {
                orderService.updateOrderToFile(completeOrder);
                storageService.writeAllToFile();
            }
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void countCompletedOrderAction() {
        try {
            LocalDate dateFrom = viewIn.readDateFrom();
            LocalDate dateTo = viewIn.readDateTo();
            int countCompletedOrder = orderService.getCountCompletedOrder(dateFrom, dateTo);
            viewOut.countCompletedOrderOut(countCompletedOrder);
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void showCompletedOrdersAction() {
        try {
            LocalDate dateFrom = viewIn.readDateFrom();
            LocalDate dateTo = viewIn.readDateTo();
            List<Order> orders = orderService.getCompletedOrder(dateFrom, dateTo);
            viewOut.printList(orders);
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void sortingOrdersAction() {
        try {
            viewOut.printList(orderService.getSortingOrderList());
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void unsortingOrdersAction() {
        try {
            viewOut.printList(orderService.getOrderList());
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void exportOrderAction() {
        try {
            orderService.writeAllToFile();
            viewOut.writeOrderListFromFile();
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void importOrderAction() {
        try {
            orderService.readAllFromFile();
            viewOut.readOrderListFromFile();
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void addRequestAction() {
        try {
            Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
            Request request = requestService.addRequest(book);
            if (saveChanging()) {
                requestService.writeRequestToFile(request);
            }
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void sortingRequestsAction() {
        try {
            viewOut.printList(requestService.getSortingRequestList());
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void unsortingRequestAction() {
        try {
            viewOut.printList(requestService.getRequestList());
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void exportRequestAction() {
        try {
            requestService.writeAllToFile();
            viewOut.writeRequestListFromFile();
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void importRequestAction() {
        try {
            requestService.readAllFromFile();
            viewOut.readRequestListFromFile();
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void addBookAction() {
        try {
            Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
            int count = viewIn.readCountBook();
            Bookshelf bookshelf = storageService.addBookOnStorage(book, count);
            if (saveChanging()) {
                storageService.updateBookshelfToFile(bookshelf);
                requestService.writeAllToFile();
            }
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void showUnsoldBooks() {
        try {
            viewOut.printList(storageService.getUnsoldBookshelves());
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void sortingBooksAction() {
        try {
            viewOut.printList(storageService.getSortingBookshelves());
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void unsortingBooksAction() {
        try {
            viewOut.printList(storageService.getBookshelfList());
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void exportBookshelfAction() {
        try {
            storageService.writeAllToFile();
            viewOut.writeBookshelfListFromFile();
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
    }

    public void importBookshelfAction() {
        try {
            storageService.readAllFromFile();
            viewOut.readBookshelfListFromFile();
        } catch (RepositoryException e) {
            viewOut.printExceptionMessage(e.getMessage());
        }
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
