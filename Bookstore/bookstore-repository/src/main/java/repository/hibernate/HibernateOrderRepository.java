package repository.hibernate;

import entity.Order;
import org.springframework.stereotype.Repository;
import repository.base.OrderRepository;

@Repository
public class HibernateOrderRepository extends AbstractHibernateRepository<Order, Integer> implements OrderRepository {
}
