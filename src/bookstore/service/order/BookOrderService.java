package bookstore.service.order;

import bookstore.model.Customer;
import bookstore.model.Order;
import bookstore.model.book.Book;
import bookstore.repository.order.BookOrderRepository;
import bookstore.repository.order.OrderRepository;
import bookstore.service.request.RequestService;
import bookstore.service.storage.StorageService;
import bookstore.util.comparator.OrderCompletionDateComparator;
import bookstore.util.comparator.OrderDateComparator;
import bookstore.util.comparator.OrderPriceComparator;
import bookstore.util.comparator.OrderStatusComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.model.Status.*;

public class BookOrderService implements OrderService {

    private OrderRepository orderList;
    private StorageService storageService;
    private RequestService requestService;

    public BookOrderService(StorageService storageService, RequestService requestService) {
        this.storageService = storageService;
        this.requestService = requestService;
        orderList = new BookOrderRepository();
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
        orderList.create(bookOrder);
    }

    @Override
    public boolean cancelOrder(Order bookOrder) {
        for (int number : bookOrder.getNumbersRequest()) {
            requestService.cancelRequest(number);
        }
        return orderList.update(bookOrder, CANCELED) != null;
    }

    @Override
    public boolean completeOrder(Order bookOrder) {
        List<Integer> requestNumbers = bookOrder.getNumbersRequest();
        boolean result = requestService.checkCompleteRequest(requestNumbers);
        if (result) {
            result = orderList.update(bookOrder, COMPLETED) != null;
        }
        return result;
    }

    @Override
    public double earnedMoney(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> bookOrders = getCompletedOrder(dateFrom, dateTo);
        double money = 0;
        for (Order order : bookOrders) {
            money += storageService.getTotalPrice(order.getBooks());
        }
        return money;
    }

    @Override
    public List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> orders = searchByDate(dateFrom, dateTo);
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
            orders.sort(orderComp);
        }
        return orders;
    }

    private List<Order> searchByDate(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> orders = new ArrayList<>();
        for (Order order : getOrderList()) {
            if (isBelongsDateToRange(order.getOrderCompletionDate(), dateFrom, dateTo)) {
                orders.add(order);
            }
        }
        return orders;
    }

    private boolean isBelongsDateToRange(LocalDate date, LocalDate dateFrom, LocalDate dateTo) {
        return date != null && (date.isAfter(dateFrom) && date.isBefore(dateTo));
    }

    @Override
    public int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        return getCompletedOrder(dateFrom, dateTo).size();
    }

    @Override
    public List<Order> getOrderList() {
        return orderList.readAll();
    }

    @Override
    public List<Order> getNewOrder() {
        List<Order> orders = new ArrayList<>();
        for (Order order : orderList.readAll()) {
            if (order.getStatus() == NEW) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Order> getSortingOrderList() {
        List<Order> orders = new ArrayList<>(orderList.readAll());
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                    .thenComparing(new OrderStatusComparator());
            orders.sort(orderComp);
        }
        return orders;
    }
}
