package bookstore.repository.db;

import bookstore.constant.SqlConstant;
import bookstore.entity.Order;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.OrderRepository;
import bookstore.util.connections.ConnectionUtils;
import com.annotation.InjectByType;

import java.sql.*;
import java.util.List;

public class DBOrderRepository implements OrderRepository {

    @InjectByType
    private ConnectionUtils connectionUtils;

    @Override
    public Order create(Order order) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(String.valueOf(order.hashCode()));
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.CREATE_ORDER);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setString(2, order.getCustomer().getFullName());
            preparedStatement.setString(3, order.getCustomer().getAddress());
            preparedStatement.setString(4, order.getCustomer().getPhoneNumber());
            Date date = new Date(order.getOrderDate().toEpochDay());
            preparedStatement.setDate(5, new Date(order.getOrderDate().toEpochDay()));
            preparedStatement.setDate(6, new Date(order.getOrderCompletionDate().toEpochDay()));
            preparedStatement.setDouble(7, order.getPrice());
            preparedStatement.setString(8, order.getStatus().toString());
//            ResultSet rs = statement.executeQuery(sql);

            return null;
        } catch (ClassNotFoundException e) {
            throw new RepositoryException("Ошибка обновления заказа в файле!");
        } catch (SQLException e) {
            throw new RepositoryException("Ошибка обновления заказа в файле!");
        }
    }

    @Override
    public Order update(Order order) throws RepositoryException {
        return null;
    }

    @Override
    public Order read(Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(Integer integer) throws RepositoryException {

    }

    @Override
    public List<Order> readAll() throws RepositoryException {
        return null;
    }

    @Override
    public void createAll(List<Order> t) throws RepositoryException {

    }
}
