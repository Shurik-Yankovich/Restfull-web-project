package bookstore.repository.order;

import bookstore.model.Order;
import bookstore.model.Status;
import bookstore.repository.Repository;

public interface OrderRepository extends Repository<Order, Status, Integer, Order> {

}
