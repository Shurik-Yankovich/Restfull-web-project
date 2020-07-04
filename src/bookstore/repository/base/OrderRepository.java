package bookstore.repository.base;

import bookstore.model.Order;
import bookstore.model.Status;

public interface OrderRepository extends Repository<Order, Status, Integer, Order> {

}
