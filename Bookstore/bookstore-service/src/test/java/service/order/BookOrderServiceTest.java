package service.order;

import config.OrderServiceTestConfig;
import entity.*;
import exeption.RepositoryException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.base.OrderRepository;
import service.request.RequestService;
import service.storage.StorageService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderServiceTestConfig.class)
public class BookOrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private OrderRepository orderRepository;

    private static Order expectedOrder;
    private static Customer customer;
    private static List<Request> expectedRequestList;
    private static List<Book> expectedBookList;

    @BeforeAll
    public static void init() {
        Book book = new Book("Алхимик", "Пауло Коэльо", 2019);
        book.setId(1);
        customer = mock(Customer.class);
        expectedBookList = new ArrayList<>();
        expectedBookList.add(book);
        expectedRequestList = new ArrayList<>();
        expectedRequestList.add(new Request(book));
        expectedRequestList.get(0).setId(1);
    }

    @BeforeEach
    public void initForEach() {
        expectedOrder = new Order(customer, expectedBookList);
        expectedOrder.setId(1);
        expectedOrder.setPrice(99.9);
        expectedOrder.setRequests(expectedRequestList);
    }

    @Test
    public void addOrderWithoutAnyProblems() throws RepositoryException {
//        when(storageService.getTotalPrice(expectedBookList)).thenReturn(99.9);
        doReturn(99.9).when(storageService).getTotalPrice(expectedBookList);
        when(storageService.checkBooksNotInStorage(expectedBookList)).thenReturn(expectedBookList);
        when(requestService.addRequestList(expectedBookList)).thenReturn(expectedRequestList);
        doReturn(expectedOrder).when(orderRepository).create(any(Order.class));
        Order actualOrder = orderService.addOrder(customer, expectedBookList.get(0));
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void addOrderWithExceptionToGetTotalPriceInStorageService() throws RepositoryException {
        when(storageService.getTotalPrice(expectedBookList)).thenThrow(RepositoryException.class);
        Order actualOrder = orderService.addOrder(customer, expectedBookList.get(0));
        assertNull(actualOrder);
    }

    @Test
    public void cancelOrderChecksIfTheOrderStatusHasBeenChanged() throws RepositoryException {
        doNothing().when(storageService).cancelBookReservation(expectedOrder);
        when(requestService.cancelRequest(any(Request.class))).thenAnswer((invocation -> invocation.getArguments()[0]));
        when(orderRepository.update(any(Order.class))).thenAnswer((invocation -> invocation.getArguments()[0]));
        Order actualOrder = orderService.cancelOrder(expectedOrder);
        assertEquals(Status.CANCELED, actualOrder.getStatus());
    }

    @Test
    public void cancelOrderWithExceptionToCancelBookReservationInStorageService() throws RepositoryException {
        doThrow(RepositoryException.class).when(storageService).cancelBookReservation(expectedOrder);
        Order actualOrder = orderService.cancelOrder(expectedOrder);
        assertNull(actualOrder);
    }

    @Test
    public void completeOrderWhenOrderRequestsAreCompleted() throws RepositoryException {
        when(orderRepository.update(any(Order.class))).thenAnswer((invocation -> invocation.getArguments()[0]));
        expectedOrder.getRequests().get(0).setStatus(Status.COMPLETED);
        Order actualOrder = orderService.completeOrder(expectedOrder);
        assertEquals(Status.COMPLETED, actualOrder.getStatus());
    }

    @Test
    public void completeOrderWhenOrderRequestsAreNotCompleted() throws RepositoryException {
        when(orderRepository.update(any(Order.class))).thenAnswer((invocation -> invocation.getArguments()[0]));
        Order actualOrder = orderService.completeOrder(expectedOrder);
        assertNull(actualOrder);
    }

    @Test
    public void earnedMoneyWhenThereAreCompletedOrdersInAGivenPeriodOfTime() throws RepositoryException {
        List<Order> orderList = new ArrayList<>();
        expectedOrder.setOrderCompletionDate(LocalDate.parse("2020-08-20"));
        orderList.add(expectedOrder);
        doReturn(orderList).when(orderRepository).readAll();
        Double actualEarnedMoney = orderService.earnedMoney(LocalDate.parse("2020-01-20"), LocalDate.parse("2020-09-20"));
        assertEquals(99.9, actualEarnedMoney);
    }

    @Test
    public void earnedMoneyWhenThereAreNoCompletedOrders() throws RepositoryException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(expectedOrder);
        doReturn(orderList).when(orderRepository).readAll();
        Double actualEarnedMoney = orderService.earnedMoney(LocalDate.parse("2020-01-20"), LocalDate.parse("2020-09-20"));
        assertEquals(0, actualEarnedMoney);
    }

    @Test
    public void earnedMoneyWithExceptionToReadAllFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(orderRepository).readAll();
        Double actualEarnedMoney = orderService.earnedMoney(LocalDate.parse("2020-01-20"), LocalDate.parse("2020-09-20"));
        assertEquals(-1, actualEarnedMoney);
    }

    @Test
    public void getCompletedOrderWhenThereAreCompletedOrdersInAGivenPeriodOfTime() throws RepositoryException {
        List<Order> expectedOrderList = new ArrayList<>();
        expectedOrder.setOrderCompletionDate(LocalDate.parse("2020-08-20"));
        expectedOrderList.add(expectedOrder);
        doReturn(expectedOrderList).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getCompletedOrder(LocalDate.parse("2020-01-20"), LocalDate.parse("2020-09-20"));
        assertEquals(expectedOrderList, actualOrderList);
    }

    @Test
    public void getCompletedOrderWithExceptionToReadAllFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getCompletedOrder(LocalDate.parse("2020-01-20"), LocalDate.parse("2020-09-20"));
        assertNull(actualOrderList);
    }

    @Test
    public void getCountCompletedOrderWhenThereAreCompletedOrdersInAGivenPeriodOfTime() throws RepositoryException {
        List<Order> expectedOrderList = new ArrayList<>();
        expectedOrder.setOrderCompletionDate(LocalDate.parse("2020-08-20"));
        expectedOrderList.add(expectedOrder);
        doReturn(expectedOrderList).when(orderRepository).readAll();
        int actualCountCompletedOrder = orderService.getCountCompletedOrder(LocalDate.parse("2020-01-20"), LocalDate.parse("2020-09-20"));
        assertEquals(1, actualCountCompletedOrder);
    }

    @Test
    public void getOrderListWithoutProblemReadFromDatabase() throws RepositoryException {
        List<Order> expectedOrderList = new ArrayList<>();
        expectedOrderList.add(expectedOrder);
        doReturn(expectedOrderList).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getOrderList();
        assertEquals(expectedOrderList, actualOrderList);
    }

    @Test
    public void getOrderListWithExceptionReadFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getOrderList();
        assertNull(actualOrderList);
    }

    @Test
    public void getNewOrdersWithoutProblemReadFromDatabaseAndStatusResultRequestsWillBeNew() throws RepositoryException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(expectedOrder);
        Order order = new Order(customer, expectedBookList);
        order.setId(2);
        order.setStatus(Status.COMPLETED);
        orderList.add(order);
        List<Order> expectedOrderList = new ArrayList<>();
        expectedOrderList.add(expectedOrder);
        doReturn(orderList).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getNewOrders();
        assertEquals(expectedOrderList, actualOrderList);
        assertEquals(Status.NEW, actualOrderList.get(0).getStatus());
    }

    @Test
    public void getNewOrdersWithExceptionReadFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getNewOrders();
        assertNull(actualOrderList);
    }

    @Test
    public void getSortingOrderListWithoutProblemReadFromDatabase() throws RepositoryException {
        List<Order> expectedOrderList = new ArrayList<>();
        expectedOrderList.add(expectedOrder);
        doReturn(expectedOrderList).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getSortingOrderList();
        assertEquals(expectedOrderList, actualOrderList);
    }

    @Test
    public void getSortingOrderListWithExceptionReadFromDatabase() throws RepositoryException {
        doThrow(RepositoryException.class).when(orderRepository).readAll();
        List<Order> actualOrderList = orderService.getSortingOrderList();
        assertNull(actualOrderList);
    }
}
