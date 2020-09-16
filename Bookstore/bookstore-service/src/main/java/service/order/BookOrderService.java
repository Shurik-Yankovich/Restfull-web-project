package service.order;

import entity.Book;
import entity.Customer;
import entity.Order;
import entity.Request;
import exeption.RepositoryException;
import logger.LoggerApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import repository.base.OrderRepository;
import repository.file.FileOrderRepository;
import service.request.RequestService;
import service.storage.StorageService;
import util.comparator.OrderCompletionDateComparator;
import util.comparator.OrderDateComparator;
import util.comparator.OrderPriceComparator;
import util.comparator.OrderStatusComparator;
import util.serialize.ISerializationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static entity.Status.*;

public class BookOrderService implements OrderService {

    @Value("${ORDER_SERIALIZATION_FILE_NAME}")
    private String ORDER_SERIALIZATION_FILE_NAME;

    @Autowired
    private RequestService requestService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FileOrderRepository fileOrderRepository;
    @Autowired
    private ISerializationService<Order> orderSerialize;

    private final LoggerApp logger = new LoggerApp(this.getClass());

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
            List<Request> numbersRequest = requestService.addRequestList(books);
            bookOrder.setRequests(numbersRequest);
            orderRepository.create(bookOrder);
            return bookOrder;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public Order cancelOrder(Order bookOrder) {
        try {
            storageService.cancelBookReservation(bookOrder);
            for (Request request : bookOrder.getRequests()) {
//                Request request = requestService.getRequestByNumber(request);
                requestService.cancelRequest(request);
            }
            bookOrder.setStatus(CANCELED);
            return orderRepository.update(bookOrder);
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public Order completeOrder(Order bookOrder) {
        try {
            List<Request> requestNumbers = bookOrder.getRequests();
//            boolean result = requestService.checkCompleteRequest(requestNumbers);
            boolean result = checkCompleteRequest(requestNumbers);
            if (result) {
                bookOrder.setStatus(COMPLETED);
                bookOrder = orderRepository.update(bookOrder);
                result = bookOrder != null;
            }
            return result ? bookOrder : null;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    private boolean checkCompleteRequest(List<Request> requestNumbers) {
        if (requestNumbers != null) {
            for (Request request : requestNumbers) {
                if (request.getStatus() != COMPLETED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public double earnedMoney(LocalDate dateFrom, LocalDate dateTo) {
        List<Order> bookOrders = getCompletedOrder(dateFrom, dateTo);
        if (bookOrders != null) {
            if (bookOrders.size() > 0) {
                double money = 0;
                for (Order order : bookOrders) {
                    money += order.getPrice();
                }
                return money;
            } else {
                return 0;
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
            logger.errorLogger(e.getMessage());
            return null;
        }
    }

    private List<Order> searchByDate(LocalDate dateFrom, LocalDate dateTo) throws RepositoryException {
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
        if (orderList != null) {
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
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
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
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeAllToFile() {
        try {
            fileOrderRepository.createAll(orderRepository.readAll());
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeOrderToFile(Order order) {
        try {
            fileOrderRepository.create(order);
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateOrderToFile(Order order) {
        try {
            fileOrderRepository.update(order);
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            orderSerialize.save(orderRepository.readAll(), ORDER_SERIALIZATION_FILE_NAME);
            return true;
        } catch (RepositoryException e) {
            logger.errorLogger(e.getMessage());
            return false;
        }
    }
}
