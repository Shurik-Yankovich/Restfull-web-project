package restcontroller;

import dto.*;
import entity.Book;
import entity.Customer;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.order.OrderService;
import util.converter.DtoConverter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequestBody requestBody) {
        Customer customer = DtoConverter.convertDtoToCustomer(requestBody.getCustomerDto());
        List<Book> books = requestBody.getBookDtoList().stream().map(DtoConverter::convertDtoToBook).collect(Collectors.toList());
        final OrderDto orderDto = DtoConverter.convertOrderToDto(orderService.addOrder(new Order(customer, books)));

        return orderDto != null
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable(name = "id") int id, @RequestBody OrderDto orderDto) {
        Order order = DtoConverter.convertDtoToOrder(orderDto);
        final OrderDto resultOrderDto = DtoConverter.convertOrderToDto(orderService.cancelOrder(order));

        return resultOrderDto != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable(name = "id") int id, @RequestBody OrderDto orderDto) {
        Order order = DtoConverter.convertDtoToOrder(orderDto);
        final OrderDto resultOrderDto = DtoConverter.convertOrderToDto(orderService.completeOrder(order));

        return resultOrderDto != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/complete/earnedmoney")
    public ResponseEntity<?> getEarnedMoneyInPeriodOfTime(@RequestBody DateIntervalDto dateIntervalDto) {
        final double earnedMoney = orderService.earnedMoney(dateIntervalDto.getDateFrom(), dateIntervalDto.getDateTo());

        return earnedMoney != -1
                ? new ResponseEntity<>(earnedMoney, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<OrderDto>> getOrderList() {
        List<Order> orders = orderService.getOrderList();
        final List<OrderDto> orderDtoList = orders != null ?
                orders.stream().map(DtoConverter::convertOrderToDto).collect(Collectors.toList())
                : null;

        return orderDtoList != null && !orderDtoList.isEmpty()
                ? new ResponseEntity<>(orderDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<List<OrderDto>> getSortOrderList() {
        List<Order> orders = orderService.getSortingOrderList();
        final List<OrderDto> orderDtoList = orders != null ?
                orders.stream().map(DtoConverter::convertOrderToDto).collect(Collectors.toList())
                : null;

        return orderDtoList != null && !orderDtoList.isEmpty()
                ? new ResponseEntity<>(orderDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/complete")
    public ResponseEntity<List<OrderDto>> getOrdersWithStatusCompleted(@RequestBody DateIntervalDto dateIntervalDto) {
        List<Order> orders = orderService.getCompletedOrder(dateIntervalDto.getDateFrom(), dateIntervalDto.getDateTo());
        final List<OrderDto> orderDtoList = orders != null ?
                orders.stream().map(DtoConverter::convertOrderToDto).collect(Collectors.toList())
                : null;

        return orderDtoList != null && !orderDtoList.isEmpty()
                ? new ResponseEntity<>(orderDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/complete/count")
    public ResponseEntity<?> getCountCompletedOrders(@RequestBody DateIntervalDto dateIntervalDto) {
        final int countCompletedOrders = orderService.getCountCompletedOrder(dateIntervalDto.getDateFrom(), dateIntervalDto.getDateTo());

        return countCompletedOrders != -1
                ? new ResponseEntity<>(countCompletedOrders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/new")
    public ResponseEntity<List<OrderDto>> getOrdersWithStatusNew() {
        List<Order> orders = orderService.getNewOrders();
        final List<OrderDto> orderDtoList = orders != null ?
                orders.stream().map(DtoConverter::convertOrderToDto).collect(Collectors.toList())
                : null;

        return orderDtoList != null && !orderDtoList.isEmpty()
                ? new ResponseEntity<>(orderDtoList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
