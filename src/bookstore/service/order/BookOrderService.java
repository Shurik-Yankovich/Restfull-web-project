package bookstore.service.order;

import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.Request;
import bookstore.entity.Book;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.OrderRepository;
import bookstore.repository.file.FileOrderRepository;
import bookstore.service.request.RequestService;
import bookstore.service.storage.StorageService;
import bookstore.util.comparator.OrderCompletionDateComparator;
import bookstore.util.comparator.OrderDateComparator;
import bookstore.util.comparator.OrderPriceComparator;
import bookstore.util.comparator.OrderStatusComparator;
import bookstore.util.serialize.ISerializationService;
import com.annotation.InjectByProperty;
import com.annotation.InjectByType;
import com.annotation.PostConstruct;
import com.annotation.Singleton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static bookstore.entity.Status.*;

@Singleton
public class BookOrderService implements OrderService {

    @InjectByProperty(propertyName = "ORDER_SERIALIZATION_FILE_NAME")
    private String ORDER_SERIALIZATION_FILE_NAME;

    @InjectByType
    private RequestService requestService;
    @InjectByType
    private StorageService storageService;
    @InjectByType
    private OrderRepository orderRepository;
    @InjectByType
    private FileOrderRepository fileOrderRepository;
    @InjectByType
    private ISerializationService<Order> orderSerialize;

    @PostConstruct
    public void init() {
        try {
            orderRepository.createAll(orderSerialize.load(ORDER_SERIALIZATION_FILE_NAME));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order addOrder(Customer customer, Book... books) {
        Order bookOrder = new Order(customer, books);
        return addOrder(bookOrder);
    }

    @Override
    public Order addOrder(Order bookOrder) {
        try {
            double price = storageService.getTotalPrice(bookOrder.getBooks());
            bookOrder.setPrice(price);
            List<Book> books = storageService.checkBooksNotInStorage(bookOrder.getBooks());
            List<Integer> numbersRequest = requestService.addRequestList(books);
            bookOrder.setNumbersRequest(numbersRequest);
            orderRepository.create(bookOrder);
            return bookOrder;
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public Order cancelOrder(Order bookOrder) {
        try {
            storageService.cancelBookReservation(bookOrder);
            for (int number : bookOrder.getNumbersRequest()) {
                Request request = requestService.getRequestByNumber(number);
                requestService.cancelRequest(request);
            }
            bookOrder.setStatus(CANCELED);
            return orderRepository.update(bookOrder);
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public Order completeOrder(Order bookOrder) {
        try {
            List<Integer> requestNumbers = bookOrder.getNumbersRequest();
            boolean result = requestService.checkCompleteRequest(requestNumbers);
            if (result) {
                bookOrder.setStatus(COMPLETED);
                bookOrder = orderRepository.update(bookOrder);
                result = bookOrder != null;
            }
            return result ? bookOrder : null;
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public double earnedMoney(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> bookOrders = getCompletedOrder(dateFrom, dateTo);
        if (bookOrders!= null) {
            try {
                double money = 0;
                for (Order order : bookOrders) {
                    money += storageService.getTotalPrice(order.getBooks());
                }
                return money;
            } catch (RepositoryException e) {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo) {
        try {
            List<Order> orders = searchByDate(dateFrom, dateTo);
            if (orders.size() > 0) {
                Comparator<Order> orderComp = new OrderCompletionDateComparator().thenComparing(new OrderPriceComparator());
                orders.sort(orderComp);
            }
            return orders;
        } catch (RepositoryException e) {
            return null;
        }
    }

    private List<Order> searchByDate(LocalDate dateFrom, LocalDate dateTo) throws RepositoryException{
        List<Order> orders = new ArrayList<>();
        for (Order order : orderRepository.readAll()) {
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
        List<Order> orderList = getCompletedOrder(dateFrom, dateTo);
        if (orderList!= null) {
            return orderList.size();
        } else {
            return -1;
        }
    }

    @Override
    public List<Order> getOrderList() {
        try {
            return orderRepository.readAll();
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public List<Order> getNewOrder() {
        try {
            List<Order> orders = new ArrayList<>();
            for (Order order : orderRepository.readAll()) {
                if (order.getStatus() == NEW) {
                    orders.add(order);
                }
            }
            return orders;
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public List<Order> getSortingOrderList() {
        try {
            List<Order> orders = new ArrayList<>(orderRepository.readAll());
            if (orders.size() > 0) {
                Comparator<Order> orderComp = new OrderDateComparator().thenComparing(new OrderPriceComparator())
                        .thenComparing(new OrderStatusComparator());
                orders.sort(orderComp);
            }
            return orders;
        } catch (RepositoryException e) {
            return null;
        }
    }

    @Override
    public boolean readAllFromFile() {
        try {
            List<Order> orders = fileOrderRepository.readAll();
            orderRepository.createAll(orders);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean writeAllToFile() {
        try {
            fileOrderRepository.createAll(orderRepository.readAll());
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean writeOrderToFile(Order order) {
        try {
            fileOrderRepository.create(order);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean updateOrderToFile(Order order) {
        try {
            fileOrderRepository.update(order);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            orderSerialize.save(orderRepository.readAll(), ORDER_SERIALIZATION_FILE_NAME);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}
