package electronicbookstore.repository.order;

import electronicbookstore.model.Order;
import electronicbookstore.model.Status;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository {

    void add(Order element);
    void changeOrderStatus(Order bookOrder, Status status);
    Order get(int index);
    List<Order> getArray();
    List<Order> getCompletedOrder(LocalDate dateFrom, LocalDate dateTo);
    int size();
}
