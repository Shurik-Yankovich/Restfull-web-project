package bookstore.service.order;

import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.book.Book;
import bookstore.repository.base.OrderRepository;
import bookstore.repository.file.FileOrderRepository;
import bookstore.repository.list.BookOrderRepository;
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

import static bookstore.entity.Status.*;

public class BookOrderService implements OrderService {

    private OrderRepository orderRepository;
    private StorageService storageService;
    private RequestService requestService;
    private FileOrderRepository fileOrderRepository;

    public BookOrderService(StorageService storageService, RequestService requestService) {
        this.storageService = storageService;
        this.requestService = requestService;
        orderRepository = new BookOrderRepository();
        fileOrderRepository = new FileOrderRepository();
    }

    @Override
    public Order addOrder(Customer customer, Book... books) {
        Order bookOrder = new Order(customer, books);
        return addOrder(bookOrder);
    }

    @Override
    public Order addOrder(Order bookOrder) {
        double price = storageService.getTotalPrice(bookOrder.getBooks());
        bookOrder.setPrice(price);
        List<Book> books = storageService.checkBooksNotInStorage(bookOrder.getBooks());
        List<Integer> numbersRequest = requestService.addRequestList(books);
        bookOrder.setNumbersRequest(numbersRequest);
        orderRepository.create(bookOrder);
        return bookOrder;
    }

    @Override
    public Order cancelOrder(Order bookOrder) {
        storageService.cancelBookReservation(bookOrder);
        for (int number : bookOrder.getNumbersRequest()) {
            requestService.cancelRequest(number);
        }
        return orderRepository.update(bookOrder, CANCELED);
    }

    @Override
    public Order completeOrder(Order bookOrder) {
        List<Integer> requestNumbers = bookOrder.getNumbersRequest();
        boolean result = requestService.checkCompleteRequest(requestNumbers);
        if (result) {
            bookOrder = orderRepository.update(bookOrder, COMPLETED);
            result = bookOrder != null;
        }
        return result ? bookOrder : null;
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
        return orderRepository.readAll();
    }

    @Override
    public List<Order> getNewOrder() {
        List<Order> orders = new ArrayList<>();
        for (Order order : orderRepository.readAll()) {
            if (order.getStatus() == NEW) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Order> getSortingOrderList() {
        List<Order> orders = new ArrayList<>(orderRepository.readAll());
        if (orders.size() > 0) {
            Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                    .thenComparing(new OrderStatusComparator());
            orders.sort(orderComp);
        }
        return orders;
    }

    @Override
    public void readAllFromFile() {
        fileOrderRepository.createAll(orderRepository.readAll());
    }

    @Override
    public void writeAllToFile() {
        List<Order> orders = fileOrderRepository.readAll();
        orderRepository.createAll(orders);
    }

    @Override
    public void writeOrderToFile(Order order) {
        fileOrderRepository.create(order);
    }

    @Override
    public void updateOrderToFile(Order order) {
        fileOrderRepository.update(order, null);
    }
}
