package bookstore.repository.file;

import bookstore.entity.Order;
import bookstore.entity.Status;
import bookstore.repository.base.OrderRepository;
import bookstore.util.csv.OrderCsv;

import java.util.List;

public class FileOrderRepository implements OrderRepository {

    private OrderCsv orderCsv;

    public FileOrderRepository() {
        orderCsv = new OrderCsv();
    }

    @Override
    public Order create(Order order) {
        orderCsv.writeToCsv(order);
        return order;
    }

    @Override
    public Order update(Order order, Status status) {
        List<Order> orderList = orderCsv.readAllFromCsv();
        for (Order bookOrder : orderList) {
            if (bookOrder.getId() == order.getId()) {
                bookOrder = order;
                break;
            }
        }
        orderCsv.writeAllToCsv(orderList);
        return order;
    }

    @Override
    public Order read(Integer id) {
        return orderCsv.readFromCsv(id);
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Order> readAll() {
        return orderCsv.readAllFromCsv();
    }

    @Override
    public void createAll(List<Order> orderList) {
        orderCsv.writeAllToCsv(orderList);
    }
}
