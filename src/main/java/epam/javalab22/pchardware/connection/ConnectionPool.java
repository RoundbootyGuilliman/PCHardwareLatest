package epam.javalab22.pchardware.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

import static epam.javalab22.pchardware.util.Constant.*;

public class ConnectionPool {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(OTHER);

    private static final String URL = resourceBundle.getString(DB_URL);
    private static final String USER = resourceBundle.getString(DB_USER);
    private static final String PASSWORD = resourceBundle.getString(DB_PASS);

    private static ConnectionPool entity;
    private final int connectionsToCreate = Integer.parseInt(resourceBundle.getString(CONNS_TO_CREATE));
    private ArrayBlockingQueue<Connection> connections = new ArrayBlockingQueue<>(connectionsToCreate);

    private Logger logger = LogManager.getLogger(getClass());

    public static ConnectionPool getConnectionPool() {
        if (entity == null) entity = new ConnectionPool();
        return entity;
    }

    private ConnectionPool() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            logger.error(DRIVER_REG_MESSAGE + e.getMessage());
        }
        for (int i = 0; i < connectionsToCreate; i++) {
            connections.add(createConnection());
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error(GETCONN_MESSAGE + e.getMessage());
        }
        return connection;
    }

    public Connection getConnection() {
        Connection connection;
        if (connections.isEmpty()) {
            connection = createConnection();
        } else {
            connection = connections.poll();
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        if (connection != null) {
            connections.add(connection);
        }
    }

    public void closeAllConnections() {
        Connection connection;
        while (!connections.isEmpty()) {
            connection = connections.poll();
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(CLOSECONN_MESSAGE + e.getMessage());
            }
        }
    }
}