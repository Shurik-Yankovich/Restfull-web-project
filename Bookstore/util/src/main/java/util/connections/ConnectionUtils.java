package util.connections;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionUtils {

    Connection getConnection() throws SQLException;
    Connection getConnection(String connectionURL, String userName, String password) throws SQLException;
    void closeConnection() throws SQLException;
}
