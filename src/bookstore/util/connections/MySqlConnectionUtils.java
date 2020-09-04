package bookstore.util.connections;

import com.annotation.InjectByProperty;
import com.annotation.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class MySqlConnectionUtils implements ConnectionUtils {

    @InjectByProperty(propertyName = "CONNECTION_URL")
    private String connectionURL;
    @InjectByProperty(propertyName = "SQL_DRIVER")
    private String sqlDriver;
    @InjectByProperty(propertyName = "USER_NAME")
    private String userName;
    @InjectByProperty(propertyName = "USER_PASSWORD")
    private String password;

    private Connection connection;
    private boolean isClosed = true;

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(connectionURL, userName, password);
    }

    @Override
    public Connection getConnection(String connectionURL, String userName, String password) throws SQLException {
//        Class.forName(sqlDriver);
        if (isClosed) {
            connection = DriverManager.getConnection(connectionURL, userName, password);
            isClosed = false;
        }
        return connection;
    }

    @Override
    public void closeConnection() throws SQLException {
        if(connection != null){
            connection.close();
            isClosed = true;
        }
    }

}
