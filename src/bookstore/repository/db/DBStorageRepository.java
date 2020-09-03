package bookstore.repository.db;

import bookstore.constant.SqlConstant;
import bookstore.entity.Book;
import bookstore.entity.Bookshelf;
import bookstore.exeption.RepositoryException;
import bookstore.repository.base.StorageRepository;
import bookstore.util.connections.ConnectionUtils;
import com.annotation.InjectByType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DBStorageRepository implements StorageRepository {

    @InjectByType
    private ConnectionUtils connectionUtils;

    @Override
    public Bookshelf create(Bookshelf bookshelf) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("StorageRepository.create." + LocalDateTime.now().toString());
            bookshelf = addBookshelfToDB(connection, bookshelf);
            connection.commit();
        } catch (SQLException e) {
            try {
                bookshelf = null;
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию добавления книжной полки в бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию добавления книжной полки в бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return bookshelf;
    }

    @Override
    public Bookshelf update(Bookshelf bookshelf) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("StorageRepository.update." + LocalDateTime.now().toString());
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.UPDATE_BOOKSHELF);
            preparedStatement.setInt(1, bookshelf.getCount());
            preparedStatement.setDouble(2, bookshelf.getPrice());
            preparedStatement.setDate(3, Date.valueOf(bookshelf.getArrivalDate()));
            preparedStatement.setInt(4, bookshelf.getId());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                bookshelf = null;
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию обновления книжной полки в бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию обновления книжной полки в бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return bookshelf;
    }

    @Override
    public Bookshelf read(Integer primaryKey) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        Bookshelf bookshelf = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("StorageRepository.read." + LocalDateTime.now().toString());
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.READ_BOOKSHELF);
            preparedStatement.setInt(1, primaryKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            bookshelf = getBookshelfOnResultSet(connection, resultSet);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию чтения книжной полки из бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию чтения книжной полки из бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return bookshelf;
    }

    @Override
    public void delete(Integer primaryKey) throws RepositoryException {

    }

    @Override
    public List<Bookshelf> readAll() throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        List<Bookshelf> bookshelves = new ArrayList<>();
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("StorageRepository.readAll." + LocalDateTime.now().toString());
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.READ_ALL_BOOKSHELVES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookshelves.add(getBookshelfOnResultSet(connection, resultSet));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                bookshelves = null;
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию чтения всех книжных полок из бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию чтения всех книжных полок из бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
        return bookshelves;
    }

    @Override
    public void createAll(List<Bookshelf> bookshelves) throws RepositoryException {
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("StorageRepository.createAll." + LocalDateTime.now().toString());
            for (Bookshelf bookshelf : bookshelves) {
                addBookshelfToDB(connection, bookshelf);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
                connection.releaseSavepoint(savepoint);
                throw new RepositoryException("Неудалось завершить транзакцию добавления книжных полок в бд!");
            } catch (SQLException ex) {
                throw new RepositoryException("Неудалось отменить транзакцию добавления книжных полок в бд!");
            }
        } finally {
            try {
                connectionUtils.closeConnection();
            } catch (SQLException e) {
                throw new RepositoryException("Неудалось закрыть соединение с бд!");
            }
        }
    }

    private Bookshelf addBookshelfToDB(Connection connection, Bookshelf bookshelf) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.CREATE_BOOKSHELF);
        Book book = bookshelf.getBook();
        preparedStatement.setInt(1, bookshelf.getId());
        preparedStatement.setInt(2, book.getId());
        preparedStatement.setInt(3, bookshelf.getCount());
        preparedStatement.setDouble(4, bookshelf.getPrice());
        preparedStatement.setDate(5, Date.valueOf(bookshelf.getArrivalDate()));
        preparedStatement.execute();
        //добавляем книгу
        preparedStatement = connection.prepareStatement(SqlConstant.CREATE_BOOK);
        preparedStatement.setInt(1, book.getId());
        preparedStatement.setString(2, book.getTitle());
        preparedStatement.setString(3, book.getAuthor());
        preparedStatement.setInt(4, book.getPublicationYear());
        preparedStatement.execute();
        return bookshelf;
    }

    private Bookshelf getBookshelfOnResultSet(Connection connection, ResultSet rs) throws SQLException {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setId(rs.getInt("id"));
        int bookId = rs.getInt("book_id");
        bookshelf.setCount(rs.getInt("count"));
        bookshelf.setPrice(rs.getDouble("price"));
        bookshelf.setArrivalDate(convertDateToLocalDate(rs.getDate("arrival_date")));
        //получаем книгу
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.READ_BOOK);
        preparedStatement.setInt(1, bookId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublicationYear(resultSet.getInt("publicationYear"));
        bookshelf.setBook(book);
        return bookshelf;
    }

    private LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}