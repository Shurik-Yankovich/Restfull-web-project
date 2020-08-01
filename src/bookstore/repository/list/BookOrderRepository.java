package bookstore.repository.list;

import bookstore.entity.Order;
import bookstore.entity.Status;
import bookstore.repository.base.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static bookstore.entity.Status.COMPLETED;

public class BookOrderRepository implements OrderRepository {

    private List<Order> array;

    public BookOrderRepository() {
        array = new ArrayList<>();
    }

//    public BookOrderRepository(List<Order> array) {
//        this.array = array;
//    }

    @Override
    public Order create(Order order) {
        int index = array.size();
        order.setId(index);
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
    public void createAll(List<Order> orders) {
        array = orders;
    }

    @Override
    public String toString() {
        return array.toString();
    }

}
