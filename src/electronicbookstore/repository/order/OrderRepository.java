package electronicbookstore.repository.order;

import electronicbookstore.model.Order;
import electronicbookstore.model.Status;

import java.util.Calendar;

public interface OrderRepository {

    void add(Order element);
    void changeOrderStatus(Order bookOrder, Status status);
    Order get(int index);
    Order[] getArray();
    Order[] getCompletedOrder(Calendar dateFrom, Calendar dateTo);
    int size();
}
