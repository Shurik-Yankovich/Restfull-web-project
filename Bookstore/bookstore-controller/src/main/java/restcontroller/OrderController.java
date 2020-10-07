package restcontroller;

import entity.Book;
import entity.Customer;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.order.OrderService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/")
    public ResponseEntity<?> create(@RequestBody Customer customer, @RequestBody List<Book> books) {
        final Order order = orderService.addOrder(new Order(customer, books));

        return order != null
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable(name = "id") int id, @RequestBody Order order) {
        final Order resultOrder = orderService.cancelOrder(order);

        return resultOrder != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable(name = "id") int id, @RequestBody Order order) {
        final Order resultOrder = orderService.completeOrder(order);

        return resultOrder != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    public ResponseEntity<Double> getMoney(@RequestBody LocalDate dateFrom, @RequestBody LocalDate dateTo) {
        final Double earnedMoney = orderService.earnedMoney(dateFrom, dateTo);

        return earnedMoney != -1
                ? new ResponseEntity<>(earnedMoney, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Order>> showList() {
        final List<Order> orderList = orderService.getOrderList();

        return orderList != null && !orderList.isEmpty()
                ? new ResponseEntity<>(orderList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<Order>> showSortList() {
        final List<Order> orderList = orderService.getSortingOrderList();

        return orderList != null && !orderList.isEmpty()
                ? new ResponseEntity<>(orderList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/complete")
    public ResponseEntity<List<Order>> showCompletedList(@RequestBody LocalDate dateFrom, @RequestBody LocalDate dateTo) {
        final List<Order> orderList = orderService.getCompletedOrder(dateFrom, dateTo);

        return orderList != null && !orderList.isEmpty()
                ? new ResponseEntity<>(orderList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/complete/count")
    public ResponseEntity<Integer> showCountCompleted(@RequestBody LocalDate dateFrom, @RequestBody LocalDate dateTo) {
        final Integer countCompletedOrders = orderService.getCountCompletedOrder(dateFrom, dateTo);

        return countCompletedOrders != -1
                ? new ResponseEntity<>(countCompletedOrders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/new")
    public ResponseEntity<List<Order>> showNewList() {
        final List<Order> orderList = orderService.getNewOrders();

        return orderList != null && !orderList.isEmpty()
                ? new ResponseEntity<>(orderList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
