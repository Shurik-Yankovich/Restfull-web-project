package repository.hibernate;

import entity.Order;
import repository.base.OrderRepository;

public class HibernateOrderRepository extends AbstractHibernateRepository<Order, Integer> implements OrderRepository {
}
