package epam.javalab22.pchardware.dao.impl;

import epam.javalab22.pchardware.connection.ConnectionPool;
import epam.javalab22.pchardware.dao.IReviewDAO;
import epam.javalab22.pchardware.entity.Review;
import epam.javalab22.pchardware.util.Closer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import static epam.javalab22.pchardware.util.Constant.*;

public class ReviewDAO implements IReviewDAO {

    private final String FIND_REVIEWS_BY_PRODUCT = "SELECT * FROM reviews WHERE product_id=? ORDER BY review_id DESC";
    private final String ADD_REVIEW = "INSERT INTO reviews (username, product_id, review, date) VALUES (?,?,?,?)";

    private ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public List<Review> getProductReviews(int productId) {
        logger.traceEntry(SEARCH_REVIEWS, productId);
        Connection connection = connectionPool.getConnection();
        ResultSet resultSet = null;
        List<Review> reviewList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_REVIEWS_BY_PRODUCT)) {
            preparedStatement.setInt(1, productId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviewList.add(new Review(resultSet.getString(2), resultSet.getString(4), resultSet.getLong(5)));
            }
        } catch (SQLException e) {
            logger.error(DB_ERROR_MESSAGE + e.getMessage());
        } finally {
            Closer.closeResultSet(resultSet);
            connectionPool.returnConnection(connection);
        }
        logger.traceExit(REVEWS_COUNT, reviewList.size());
        return reviewList;
    }

    @Override
    public void addReview(String username, int productId, String review, long time) {
        logger.traceEntry(ADDING_REVIEW, username, productId, time);
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_REVIEW)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, productId);
            preparedStatement.setString(3, review);
            preparedStatement.setLong(4, time);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(PREPARED_STAT_MESSAGE + e.getMessage());
        } finally {
            connectionPool.returnConnection(connection);
        }
        logger.traceExit();
    }
}