package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.dao.IReviewDAO;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.entity.Review;
import epam.javalab22.pchardware.service.BasicService;
import epam.javalab22.pchardware.util.TimeAndDateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static epam.javalab22.pchardware.util.Constant.*;

public class ProductShowService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();

        IProductDAO productDAO = (IProductDAO)context.getAttribute(PRODUCT_DAO);
        IReviewDAO reviewDAO = (IReviewDAO)context.getAttribute(REVIEW_DAO);

        int id = Integer.parseInt(request.getParameter(ID));
        Locale locale = (Locale) session.getAttribute(LOCALE);

        Product productToShow = productDAO.findById(id, locale.toString());
        request.setAttribute(PRODUCT, productToShow);

        List<Review> reviewList = reviewDAO.getProductReviews(id);

        Iterator<Review> iterator = reviewList.iterator();
        while (iterator.hasNext()) {
            TimeAndDateHandler.setDate(iterator.next(), locale);
        }

        request.setAttribute(REVIEWS, reviewList);

        logger.traceExit();
        return PRODUCT_JSP;
    }
}