package bookstore.repository.list;

import bookstore.entity.Order;
import bookstore.repository.base.OrderRepository;

import java.time.LocalDate;
import java.util.List;

import static bookstore.entity.Status.COMPLETED;

public class BookOrderRepository implements OrderRepository {

    private List<Order> array;

    @Override
    public Order create(Order order) {
        int index = array.size();
        order.setId(index);
        array.add(order);
        return order;
    }

    @Override
    public Order update(Order bookOrder) {
        int index = bookOrder.getId();
        if (array.get(index) != null) {
            array.set(index, bookOrder);
            if (bookOrder.getStatus() == COMPLETED) {
                bookOrder.setOrderCompletionDate(LocalDate.now());
            }
            return bookOrder;
        }
        return null;
    }

    @Override
    public Order read(Integer index) {
        return array.get(index);
    }

    @Override
    public void delete(Integer index) {
        array.remove(index);
    }

    @Override
    public List<Order> readAll() {
        return array;
    }

    @Override
    public void createAll(List<Order> orders) {
        array = orders;
    }

    @Override
    public String toString() {
        return array.toString();
    }

}
