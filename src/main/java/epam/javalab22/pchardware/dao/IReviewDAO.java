package epam.javalab22.pchardware.dao;

import epam.javalab22.pchardware.entity.Review;

import java.util.List;

public interface IReviewDAO {
    List<Review> getProductReviews(int productId);
    void addReview(String username, int productId, String review, long time);
}
