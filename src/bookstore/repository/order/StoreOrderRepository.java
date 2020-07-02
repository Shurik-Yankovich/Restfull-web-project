package bookstore.repository.order;

import bookstore.model.Order;
import bookstore.model.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static bookstore.model.Status.COMPLETED;

public class StoreOrderRepository implements OrderRepository {

    private List<Order> array;

    public StoreOrderRepository() {
        this.array = new ArrayList<>();
    }

    @Override
    public Order create(Order order) {
        array.add(order);
        return order;
    }

    @Override
    public Order update(Order bookOrder, Status status) {
        Order order = null;
        if (array.contains(bookOrder)) {
            int index = array.indexOf(bookOrder);
            order = array.get(index);
            order.setStatus(status);
            if (status == COMPLETED) {
                order.setOrderCompletionDate(LocalDate.now());
            }
        }
        return order;
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
    public void createAll(List<Order> list) {
        array.addAll(list);
    }

    @Override
    public String toString() {
        return array.toString();
    }

}
