package bookstore.service.order;

import bookstore.model.Customer;
import bookstore.model.Order;
import bookstore.model.book.Book;
import bookstore.repository.order.ListOrderRepository;
import bookstore.repository.order.OrderRepository;
import bookstore.util.comparator.OrderCompletionDateComparator;
import bookstore.util.comparator.OrderDateComparator;
import bookstore.util.comparator.OrderPriceComparator;
import bookstore.util.comparator.OrderStatusComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.model.Status.*;
import static bookstore.service.request.BookRequestService.REQUEST_SERVICE;
import static bookstore.service.storage.BookStorageService.STORAGE_SERVICE;

public class BookOrderService implements OrderService {

    public static final OrderService ORDER_SERVICE = new BookOrderService();

    private OrderRepository orderList;

    private BookOrderService() {
        orderList = new ListOrderRepository();
    }

    @Override
    public void addOrder(Customer customer, Book... books) {
        Order bookOrder = new Order(customer, books);
        addOrder(bookOrder);
    }

    @Override
    public void addOrder(Order bookOrder) {
        double price = STORAGE_SERVICE.getTotalPrice(bookOrder.getBooks());
        bookOrder.setPrice(price);
        List<Book> books = STORAGE_SERVICE.checkBooksNotOnStorage(bookOrder.getBooks());
        List<Integer> numbersRequest = REQUEST_SERVICE.addRequestList(books);
        bookOrder.setNumbersRequest(numbersRequest);
        orderList.add(bookOrder);
    }

    @Override
    public boolean cancelOrder(Order bookOrder) {
        for (int number : bookOrder.getNumbersRequest()) {
            REQUEST_SERVICE.cancelRequest(number);
        }
        return orderList.changeStatus(bookOrder, CANCELED);
    }

    @Override
    public boolean completeOrder(Order bookOrder) {
        List<Integer> requestNumbers = bookOrder.getNumbersRequest();
        boolean result = REQUEST_SERVICE.checkCompleteRequest(requestNumbers);
        if (result) {
            result = orderList.changeStatus(bookOrder, COMPLETED);
        }
        return result;
    }

    @Override
    public double earnedMoney(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> bookOrders = getCompletedOrder(dateFrom, dateTo);
        double money = 0;
        for (Order order : bookOrders) {
            money += STORAGE_SERVICE.getTotalPrice(order.getBooks());
        }
        return money;
    }

    @Override
    public List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> orders = orderList.getCompletedOrders(dateFrom, dateTo);
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
            orders.sort(orderComp);
        }
        return orders;
    }

    @Override
    public int getCountCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        return getCompletedOrder(dateFrom, dateTo).size();
    }

    @Override
    public List<Order> getOrderList() {
        return orderList.getAll();
    }

    @Override
    public List<Order> getNewOrder() {
        List<Order> orders = new ArrayList<>();
        for (Order order : orderList.getAll()) {
            if (order.getStatus() == NEW) {
                orders.add(order);
            }
        }
        return orders;
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
}
