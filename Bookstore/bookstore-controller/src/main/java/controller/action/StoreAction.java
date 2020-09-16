package controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import service.order.OrderService;
import service.request.RequestService;
import service.storage.StorageService;
import view.in.ViewIn;
import view.out.ViewOut;
import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StoreAction {

    @Autowired
    private ViewIn viewIn;
    @Autowired
    private ViewOut viewOut;
    @Autowired
    private RequestService requestService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private OrderService orderService;

    public StoreAction() {
    }

    public void earnedMoneyAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        double money = orderService.earnedMoney(dateFrom, dateTo);
        viewOut.earnedMoneyOut(money);
    }

    public void exitAction() {
        if (!requestService.save()) {
            viewOut.printExceptionMessage("Неудалось сохранить список запросов!");
        }
        if (!orderService.save()) {
            viewOut.printExceptionMessage("Неудалось сохранить список заказов!");
        }
        if (!storageService.save()) {
            viewOut.printExceptionMessage("Неудалось сохранить список книг!");
        }
        System.exit(1);
    }

    public void addOrderAction() {
        Order order = orderService.addOrder(createOrder());
        if (order != null) {
            if (saveChanging()) {
                if (!orderService.writeOrderToFile(order)) {
                    viewOut.printExceptionMessage("Неудалось добавить в файл заказ!");
                }
                if (!storageService.writeAllToFile()) {
                    viewOut.printExceptionMessage("Неудалось записать в файл список книг!");
                }
                if (!requestService.writeAllToFile()) {
                    viewOut.printExceptionMessage("Неудалось записать в файл список запросов!");
                }
            }
        } else {
            viewOut.printExceptionMessage("Неудалось создать заказ!");
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
        List<Order> orderList = orderService.getNewOrder();
        if (orderList != null) {
            Order order = viewIn.choiceFromList(orderList);
            if (order != null) {
                Order cancelOrder = orderService.cancelOrder(order);
                boolean isCanceled = cancelOrder != null;
                viewOut.cancelOrderOut(isCanceled);
                if (isCanceled && saveChanging()) {
                    if (!orderService.updateOrderToFile(cancelOrder)) {
                        viewOut.printExceptionMessage("Неудалось обновить данные по заказу в файле!");
                    }
//                for (int number : cancelOrder.getRequests()) {
                    for (Request request : cancelOrder.getRequests()) {
//                    Request request = requestService.getRequestByNumber(request1);
                        if (request == null || !requestService.updateRequestToFile(request)) {
                            viewOut.printExceptionMessage("Неудалось обновить данные по запросу в файле!");
                        }
                    }
                    if (!storageService.writeAllToFile()) {
                        viewOut.printExceptionMessage("Неудалось записать в файл список книг!");
                    }
                }
            }
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка заказов!");
        }

    }

    public void completeOrderAction() {
        List<Order> orderList = orderService.getNewOrder();
        if (orderList != null) {
            Order order = viewIn.choiceFromList(orderList);
            if (order != null) {
                Order completeOrder = orderService.completeOrder(order);
                boolean isCompleted = completeOrder != null;
                viewOut.completeOrderOut(isCompleted);
                if (isCompleted && saveChanging()) {
                    if (!orderService.updateOrderToFile(completeOrder)) {
                        viewOut.printExceptionMessage("Неудалось обновить данные по заказу в файле!");
                    }
                    if (!storageService.writeAllToFile()) {
                        viewOut.printExceptionMessage("Неудалось записать в файл список книг!");
                    }
                }
            }
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка заказов!");
        }
    }

    public void countCompletedOrderAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        int countCompletedOrder = orderService.getCountCompletedOrder(dateFrom, dateTo);
        if (countCompletedOrder != -1) {
            viewOut.countCompletedOrderOut(countCompletedOrder);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка заказов!");
        }
    }

    public void showCompletedOrdersAction() {
        LocalDate dateFrom = viewIn.readDateFrom();
        LocalDate dateTo = viewIn.readDateTo();
        List<Order> orders = orderService.getCompletedOrder(dateFrom, dateTo);
        if (orders != null) {
            viewOut.printList(orders);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка заказов!");
        }
    }

    public void sortingOrdersAction() {
        List<Order> orderList = orderService.getSortingOrderList();
        if (orderList != null) {
            viewOut.printList(orderList);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка заказов!");
        }
    }

    public void unsortingOrdersAction() {
        List<Order> orderList = orderService.getOrderList();
        if (orderList != null) {
            viewOut.printList(orderList);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка заказов!");
        }
    }

    public void exportOrderAction() {
        if (orderService.writeAllToFile()) {
            viewOut.writeOrderListFromFile();
        } else {
            viewOut.printExceptionMessage("Неудалось записать в файл список заказов!");
        }
    }

    public void importOrderAction() {
        if (orderService.readAllFromFile()) {
            viewOut.readOrderListFromFile();
        } else {
            viewOut.printExceptionMessage("Неудалось прочитать файл со списком заказов!");
        }
    }

    public void addRequestAction() {
        Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
        Request request = requestService.addRequest(book);
        if (request != null) {
            if (saveChanging()) {
                if (!requestService.writeRequestToFile(request)) {
                    viewOut.printExceptionMessage("Неудалось добавить в файл запрос!");
                }
            }
        } else {
            viewOut.printExceptionMessage("Ошибка добавления запроса!");
        }
    }

    public void completeRequestAction() {
        List<Request> requestList = requestService.getNewRequests();
        if (requestList != null) {
            Request request = viewIn.choiceFromList(requestList);
            List<Book> book = new ArrayList<>();
            if (request != null) {
                book.add(request.getBook());
                book = storageService.checkBooksNotInStorage(book);
                if (book != null) {
                    boolean isCompleted = book.size() == 0;
                    viewOut.completeRequestOut(isCompleted);
                    if (isCompleted) {
                        request = requestService.completeRequest(request);
                        if (saveChanging()) {
                            requestService.updateRequestToFile(request);
                            Bookshelf bookshelf = storageService.getBookshelf(request.getBook());
                            storageService.updateBookshelfToFile(bookshelf);
                        }
                    }
                } else {
                    viewOut.printExceptionMessage("Ошибка выполнения запроса!");
                }
            }
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка запросов!");
        }
    }

    public void sortingRequestsAction() {
        List<Request> requestList = requestService.getSortingRequestList();
        if (requestList != null) {
            viewOut.printList(requestList);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка запросов!");
        }
    }

    public void unsortingRequestAction() {
        List<Request> requestList = requestService.getRequestList();
        if (requestList != null) {
            viewOut.printList(requestList);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка запросов!");
        }
    }

    public void exportRequestAction() {
        if (requestService.writeAllToFile()) {
            viewOut.writeRequestListFromFile();
        } else {
            viewOut.printExceptionMessage("Неудалось записать в файл список запросов!");
        }
    }

    public void importRequestAction() {
        if (requestService.readAllFromFile()) {
            viewOut.readRequestListFromFile();
        } else {
            viewOut.printExceptionMessage("Неудалось прочитать файл со списком запросов!");
        }
    }

    public void addBookAction() {
        Book book = viewIn.choiceFromList(storageService.getBookshelfList()).getBook();
        int count = viewIn.readCountBook();
        Bookshelf bookshelf = storageService.addBookOnStorage(book, count);
        if (bookshelf != null) {
            if (saveChanging()) {
                if (!storageService.updateBookshelfToFile(bookshelf)) {
                    viewOut.printExceptionMessage("Неудалось обновить данные по книге в файле!");
                }
                if (!requestService.writeAllToFile()) {
                    viewOut.printExceptionMessage("Неудалось записать в файл список запросов!");
                }
            }
        } else {
            viewOut.printExceptionMessage("Ошибка добавления книги!");
        }
    }

    public void showUnsoldBooks() {
        List<Bookshelf> bookshelves = storageService.getUnsoldBookshelves();
        if (bookshelves != null) {
            viewOut.printList(bookshelves);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка книг!");
        }
    }

    public void sortingBooksAction() {
        List<Bookshelf> bookshelves = storageService.getSortingBookshelves();
        if (bookshelves != null) {
            viewOut.printList(bookshelves);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка книг!");
        }
    }

    public void unsortingBooksAction() {
        List<Bookshelf> bookshelves = storageService.getBookshelfList();
        if (bookshelves != null) {
            viewOut.printList(bookshelves);
        } else {
            viewOut.printExceptionMessage("Ошибка вывода списка книг!");
        }
    }

    public void exportBookshelfAction() {
        if (storageService.writeAllToFile()) {
            viewOut.writeBookshelfListFromFile();
        } else {
            viewOut.printExceptionMessage("Неудалось записать в файл список книг!");
        }
    }

    public void importBookshelfAction() {
        if (storageService.readAllFromFile()) {
            viewOut.readBookshelfListFromFile();
        } else {
            viewOut.printExceptionMessage("Неудалось прочитать файл со списком книг!");
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
