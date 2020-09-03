package bookstore.repository.db;

import bookstore.constant.SqlConstant;
import bookstore.entity.Book;
import bookstore.entity.Request;
import bookstore.entity.Status;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.RequestRepository;
import bookstore.util.connections.ConnectionUtils;
import com.annotation.InjectByType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBRequestRepository implements RequestRepository {

    @InjectByType
    private ConnectionUtils connectionUtils;

    @Override
    public Request create(Request request) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("RequestRepository.create." + LocalDateTime.now().toString());
            request = addRequestToDB(connection, request);
            connection.commit();
        } catch (SQLException e) {
            try {
                request = null;
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию добавления запроса в бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию добавления запроса в бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return request;
    }

    @Override
    public Request update(Request request) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("RequestRepository.update." + LocalDateTime.now().toString());
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.UPDATE_REQUEST);
            preparedStatement.setInt(1, request.getCount());
            preparedStatement.setString(2, request.getStatus().toString());
            preparedStatement.setInt(3, request.getId());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                request = null;
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию обновления запроса в бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию обновления запроса в бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return request;
    }

    @Override
    public Request read(Integer primaryKey) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        Request request = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("RequestRepository.read." + LocalDateTime.now().toString());
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.READ_REQUEST);
            preparedStatement.setInt(1, primaryKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            request = getRequestOnResultSet(connection, resultSet);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию чтения запроса из бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию чтения запроса из бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return request;
    }

    @Override
    public void delete(Integer integer) throws RepositoryException {

    }

    @Override
    public List<Request> readAll() throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        List<Request> requestList = new ArrayList<>();
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("RequestRepository.readAll." + LocalDateTime.now().toString());
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.READ_ALL_REQUEST);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                requestList.add(getRequestOnResultSet(connection, resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                requestList = null;
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию чтения всех запросов из бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию чтения всех запросов из бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return requestList;
    }

    @Override
    public void createAll(List<Request> requestList) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("RequestRepository.createAll." + LocalDateTime.now().toString());
            for (Request request : requestList) {
                addRequestToDB(connection, request);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию добавления запросов в бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию добавления запросов в бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
    }

    private Request addRequestToDB(Connection connection, Request request) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.CREATE_REQUEST);
        int requestId = request.getId();
        preparedStatement.setInt(1, requestId);
        preparedStatement.setInt(2, request.getBook().getId());
        preparedStatement.setInt(3, request.getCount());
        preparedStatement.setString(4, request.getStatus().toString());
        preparedStatement.execute();
        return request;
    }

    private Request getRequestOnResultSet(Connection connection, ResultSet rs) throws SQLException {
        Request request = new Request();
        request.setId(rs.getInt("id"));
        int bookId = rs.getInt("book_id");
        request.setCount(rs.getInt("count"));
        request.setStatus(Status.valueOf(rs.getString("status")));
        //получаем список книг для заказа
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.READ_BOOK);
        preparedStatement.setInt(1, bookId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublicationYear(resultSet.getInt("publicationYear"));
        request.setBook(book);
        return request;
    }
}
