package bookstore.repository.base;

import bookstore.entity.Order;
import bookstore.entity.Status;

public interface OrderRepository extends Repository<Order, Status, Integer, Order> {

}
