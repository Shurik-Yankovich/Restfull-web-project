package bookstore.repository.file;

import bookstore.entity.Order;
import bookstore.entity.Status;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.OrderRepository;
import bookstore.util.csv.OrderCsv;
import com.annotation.InjectByType;

import java.io.IOException;
import java.util.List;

public class FileOrderRepository implements OrderRepository {

    @InjectByType
    private OrderCsv orderCsv;

    public FileOrderRepository() {
        orderCsv = new OrderCsv();
    }

    public FileOrderRepository(OrderCsv orderCsv) {
        this.orderCsv = orderCsv;
    }

    @Override
    public Order create(Order order) throws RepositoryException {
        try {
            List<Order> orderList = orderCsv.readAllFromCsv();
            for (Order bookOrder : orderList) {
                if (bookOrder.getId() == order.getId()) {
                    order.setId(orderList.size());
                }
            }
            orderCsv.writeToCsv(order);
            return order;
        } catch (IOException e) {
            throw new RepositoryException("Ошибка записи заказа в файл!");
        }
    }

    @Override
    public Order update(Order order, Status status) throws RepositoryException {
        try {
            List<Order> orderList = orderCsv.readAllFromCsv();
            boolean isPresent = false;
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getId() == order.getId()) {
                    orderList.set(i, order);
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                orderList.add(order);
            }
            orderCsv.writeAllToCsv(orderList);
            return order;
        } catch (IOException e) {
            throw new RepositoryException("Ошибка обновления заказа в файле!");
        }
    }

    @Override
    public Order read(Integer id) throws RepositoryException {
        try {
            return orderCsv.readFromCsv(id);
        } catch (IOException e) {
            throw new RepositoryException("Ошибка чтения заказа из файла!");
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Order> readAll() throws RepositoryException {
        try {
            return orderCsv.readAllFromCsv();
        } catch (IOException e) {
            throw new RepositoryException("Ошибка чтения заказа из файла!");
        }
    }

    @Override
    public void createAll(List<Order> orderList) throws RepositoryException {
        try {
            orderCsv.writeAllToCsv(orderList);
        } catch (IOException e) {
            throw new RepositoryException("Ошибка записи запросов в файл!");
        }

    }
}
