package epam.javalab22.pchardware.dao.impl;

import epam.javalab22.pchardware.connection.ConnectionPool;
import epam.javalab22.pchardware.dao.IOrderDAO;
import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.entity.Order;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.util.Closer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static epam.javalab22.pchardware.util.Constant.*;

public class OrderDAO implements IOrderDAO {

    private final String INSERT_ORDER = "INSERT INTO orders (username, total_sum, status, date) VALUES (?,?,?,?)";
    private final String INSERT_ORDERED_PRODUCTS = "INSERT INTO ordered_products VALUES (?,?,?)";
    private final String FIND_ALL_ORDERS = "SELECT * FROM orders ORDER BY order_id DESC";
    private final String FIND_USER_ORDERS = "SELECT * FROM orders WHERE username=? ORDER BY order_id DESC";
    private final String FIND_ORDERED_PRODUCTS = "SELECT * FROM ordered_products WHERE order_id=?";
    private final String CHANGE_STATUS = "UPDATE orders SET status=? WHERE order_id=?";

    private final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public List<Order> getAllOrders() {

        logger.traceEntry();
        Connection connection = connectionPool.getConnection();

        List<Order> orderList = new ArrayList<>();

        int orderId;
        String username;
        String totalSum;
        String status;
        long time;
        Map<Product, Integer> mapOfProducts;

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                orderId = resultSet.getInt(1);
                username = resultSet.getString(2);
                totalSum = resultSet.getString(3);
                status = resultSet.getString(4);
                time = resultSet.getLong(5);
                mapOfProducts = getOrderedProducts(orderId);
                orderList.add(new Order(orderId, username, totalSum, status, time, mapOfProducts));
            }
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit(ORDERS_FOUND, orderList.size());
        return orderList;
    }

    @Override
    public void placeAnOrder(Map<Product, Integer> shoppingCart, String username, String totalSum, long time) {

        logger.traceEntry(PLACING, username, totalSum, time, shoppingCart.size());
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;

        String status = ORDER_STATUS_PENDING;
        int orderId;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, totalSum);
            preparedStatement.setString(3, status);
            preparedStatement.setLong(4, time);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            orderId = resultSet.getInt(1);
            for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
                insertProducts(orderId, entry.getKey().getId(), entry.getValue());
            }
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }

    private void insertProducts(int orderId, int productId, int amount) {
        logger.traceEntry(INSERTING, orderId, productId, amount);
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDERED_PRODUCTS)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, amount);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }

    @Override
    public List<Order> findOrders(String username) {
        logger.traceEntry(SEARCH_ORD, username);
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();

        int orderId;
        String totalSum;
        String status;
        long time;
        Map<Product, Integer> mapOfProducts;

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_ORDERS)) {
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderId = resultSet.getInt(1);
                totalSum = resultSet.getString(3);
                status = resultSet.getString(4);
                time = resultSet.getLong(5);
                mapOfProducts = getOrderedProducts(orderId);
                orderList.add(new Order(orderId, username, totalSum, status, time, mapOfProducts));
            }
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit(ORDERS_FOUND, orderList.size());
        return orderList;
    }

    private Map<Product, Integer> getOrderedProducts(int orderId) {
        logger.traceEntry(SEARCH_ORD_PR, orderId);
        IProductDAO productDAO = new ProductDAO();

        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;
        Map<Product, Integer> mapOfProducts = new ConcurrentHashMap<>();

        int productId;
        int amount;

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDERED_PRODUCTS)) {

            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productId = resultSet.getInt(2);
                amount = resultSet.getInt(3);
                mapOfProducts.put(productDAO.findById(productId, LOCALE_PLACEHOLDER), amount);
            }
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit(PRODUCTS_FOUND, mapOfProducts.size());
        return mapOfProducts;
    }

    @Override
    public void changeOrderStatus(String status, int orderId) {
        logger.traceEntry(CHANGING_STATUS, orderId, status);
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_STATUS)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }
}
