package bookstore.service.order;

import bookstore.entity.Customer;
import bookstore.entity.Order;
import bookstore.entity.book.Book;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.OrderRepository;
import bookstore.repository.file.FileOrderRepository;
import bookstore.repository.list.BookOrderRepository;
import bookstore.serialize.ISerializationService;
import bookstore.serialize.SerializationService;
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

import static bookstore.constant.FileName.ORDER_SERIALIZATION_FILE_NAME;
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

    public BookOrderService(StorageService storageService, RequestService requestService,
                            OrderRepository orderRepository, FileOrderRepository fileOrderRepository) {
        this.storageService = storageService;
        this.requestService = requestService;
        this.orderRepository = orderRepository;
        this.fileOrderRepository = fileOrderRepository;
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
                requestService.cancelRequest(number);
            }
            return orderRepository.update(bookOrder, CANCELED);
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
                bookOrder = orderRepository.update(bookOrder, COMPLETED);
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
            fileOrderRepository.update(order, null);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            ISerializationService<Order> orderSerialize = new SerializationService<>();
            orderSerialize.save(orderRepository.readAll(), ORDER_SERIALIZATION_FILE_NAME);
            return true;
        } catch (RepositoryException e) {
            return false;
        }
    }
}
