package epam.javalab22.pchardware.dao.impl;

import epam.javalab22.pchardware.connection.ConnectionPool;
import epam.javalab22.pchardware.dao.IUserDAO;
import epam.javalab22.pchardware.entity.User;
import epam.javalab22.pchardware.util.Closer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static epam.javalab22.pchardware.util.Constant.*;

public class UserDAO implements IUserDAO {

    private final String FIND_ALL_USERS = "SELECT * FROM users";
    private final String FIND_ALL_ROLES = "SELECT * FROM user_roles WHERE username=?";
    private final String FIND_USER = "SELECT * FROM users WHERE username=?";
    private final String EDIT_USER = "UPDATE users SET username=?, email=?, name=?, last_name=?, phone_number=?, address=? WHERE username=?";
    private final String ADD_USER = "INSERT INTO users VALUES (?,?,?,?,?,?,?)";
    private final String ADD_ROLE = "INSERT INTO user_roles (username, role) VALUES (?,?)";
    private final String DELETE_USER = "DELETE FROM users WHERE username=?";

    private ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public List<User> getAllUsers() {
        logger.traceEntry(SEARCH_USERS);
        Connection connection = connectionPool.getConnection();

        String username;
        String email;
        String name;
        String lastName;
        String phoneNumber;
        String address;
        User user;
        List<User> listOfUsers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                username = resultSet.getString(1);
                email = resultSet.getString(2);
                name = resultSet.getString(4);
                lastName = resultSet.getString(5);
                phoneNumber = resultSet.getString(6);
                address = resultSet.getString(7);
                user = new User(username, email, null, name, lastName, phoneNumber, address);
                user = getUserRoles(user);
                listOfUsers.add(user);
            }
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit(USER_COUNT, listOfUsers.size());
        return listOfUsers;
    }

    private User getUserRoles(User user) {
        logger.traceEntry(SEARCHING_ROLES, user.getUsername());
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;

        List<String> listOfRoles = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ROLES)) {
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listOfRoles.add(resultSet.getString(2));
            }
            user.setListOfRoles(listOfRoles);
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        logger.traceEntry(SEARCHING_DATA, username);
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER)) {
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6), resultSet.getString(7));
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
        return user;
    }

    @Override
    public void editUserData(User user) {
        logger.traceEntry(EDITING_DATA, user.getUsername());
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(EDIT_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }

    @Override
    public void registerUser(User user) {
        logger.traceEntry(REGISTERING, user.getUsername());
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.executeUpdate();
            addRole(user.getUsername());
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }

    private void addRole(String username) {
        logger.traceEntry(ADDING_ROLE, username);
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ROLE)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, USER_ROLE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }

    @Override
    public void deleteUser(String username) {
        logger.traceEntry(DELETING_USER, username);
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }
}
