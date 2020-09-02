package bookstore.util.connections;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionUtils {

    Connection getConnection() throws SQLException, ClassNotFoundException;
    Connection getConnection(String connectionURL, String userName, String password) throws SQLException, ClassNotFoundException;
    void closeConnection() throws SQLException;
}
