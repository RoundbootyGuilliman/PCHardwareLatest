package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IReviewDAO;
import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

import static epam.javalab22.pchardware.util.Constant.*;

public class ReviewAddService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        ServletContext context = request.getServletContext();

        IReviewDAO reviewDAO = (IReviewDAO)context.getAttribute(REVIEW_DAO);

        String username = request.getRemoteUser();
        int productId = Integer.parseInt(request.getParameter(ID));
        String review = request.getParameter(REVIEW);
        long time = Calendar.getInstance().getTimeInMillis();

        reviewDAO.addReview(username, productId, review, time);

        logger.traceExit();
        return REDIRECT_JSP;
    }
}