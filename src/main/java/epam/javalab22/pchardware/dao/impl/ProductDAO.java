package epam.javalab22.pchardware.dao.impl;

import epam.javalab22.pchardware.connection.ConnectionPool;
import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.util.Closer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import static epam.javalab22.pchardware.util.Constant.*;

public class ProductDAO implements IProductDAO {

    private final String FIND_ALL_PRODUCTS = "SELECT * FROM products ORDER BY product_id DESC";
    private final String FIND_PRODUCTS_BY_TYPE = "SELECT * FROM products WHERE type=? ORDER BY price DESC";
    private final String FIND_PRODUCTS_BY_ID = "SELECT * FROM products WHERE product_id=?";
    private final String FIND_CHARS = "SELECT * FROM chars WHERE product_id=? AND locale_id=?";
    private final String FIND_LOCALES = "SELECT * FROM locales WHERE locale_name=?";
    private final String ADD_PRODUCT = "INSERT INTO products (name, price, type, img) VALUES (?,?,?,?)";
    private final String ADD_CHAR = "INSERT INTO chars (product_id, char_id, locale_id, description_key, description_value) VALUES (?,?,?,?,?)";
    private final String DELETE_PRODUCT = "DELETE FROM products WHERE product_id=?";

    private ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public List<Product> getAllProducts() {
        logger.traceEntry();
        Connection connection = connectionPool.getConnection();

        int id;
        String name;
        int price;
        String type;
        String img;
        List<Product> listOfProducts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                name = resultSet.getString(2);
                price = resultSet.getInt(3);
                type = resultSet.getString(4);
                img = resultSet.getString(5);
                listOfProducts.add(new Product(id, name, price, type, img));
            }
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit(PRODUCTS_FOUND, listOfProducts.size());
        return listOfProducts;
    }

    @Override
    public List<Product> findByType(String type, String locale) {
        logger.traceEntry(TYPE_SEARCH, type, locale);
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;

        int id;
        String name;
        int price;
        String img;
        Product product;
        List<Product> listOfProducts = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCTS_BY_TYPE)) {
            preparedStatement.setString(1, type);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                name = resultSet.getString(2);
                price = resultSet.getInt(3);
                img = resultSet.getString(5);
                product = new Product(id, name, price, type, img);
                product = findChars(product, locale);
                listOfProducts.add(product);
            }
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit(PRODUCTS_FOUND, listOfProducts.size());
        return listOfProducts;
    }

    @Override
    public Product findById(int id, String locale) {
        logger.traceEntry(SEARCH_PR, id, locale);
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;
        Product product = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCTS_BY_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            product = new Product(id, resultSet.getString(2), resultSet.getInt(3),
                    resultSet.getString(4), resultSet.getString(5));
            product = findChars(product, locale);
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
        return product;
    }

    private Product findChars(Product product, String locale) {
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;
        Map<String, String> mapOfCharacteristics = new LinkedHashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_CHARS)) {
            preparedStatement.setString(1, String.valueOf(product.getId()));
            preparedStatement.setInt(2, getLocaleId(locale));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(2) == 1) {
                    product.setVendor(resultSet.getString(5));
                }
                mapOfCharacteristics.put(resultSet.getString(4), resultSet.getString(5));
            }
            product.setMapOfCharacteristics(mapOfCharacteristics);
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        return product;
    }

    private int getLocaleId(String locale) {
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;

        int localeId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_LOCALES)) {
            preparedStatement.setString(1, locale);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            localeId = resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        return localeId;
    }

    @Override
    public void addProduct(Product product, Map<String, String> mapOfCharsEng, Map<String, String> mapOfCharsRus) {
        logger.traceEntry(ADDING_PRODUCT, product.getName(), mapOfCharsEng.size(), mapOfCharsRus.size());
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;
        Iterator<Map.Entry<String, String>> iterator;

        int productId;

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getType());
            preparedStatement.setString(4, product.getImg());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            productId = resultSet.getInt(1);

            iterator = mapOfCharsEng.entrySet().iterator();
            for (int i = 1; i < mapOfCharsEng.entrySet().size() + 1; i++) {
                Map.Entry<String, String> entry = iterator.next();
                addChar(productId, i, LOCALE_ID_EN, entry.getKey(), entry.getValue());
            }
            iterator = mapOfCharsRus.entrySet().iterator();
            for (int i = 1; i < mapOfCharsRus.entrySet().size() + 1; i++) {
                Map.Entry<String, String> entry = iterator.next();
                addChar(productId, i, LOCALE_ID_RU, entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }

    private void addChar(int productId, int charId, int localeId, String key, String value) {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_CHAR)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, charId);
            preparedStatement.setInt(3, localeId);
            preparedStatement.setString(4, key);
            preparedStatement.setString(5, value);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        logger.traceEntry(DELETING_PRODUCT, productId);
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }
}