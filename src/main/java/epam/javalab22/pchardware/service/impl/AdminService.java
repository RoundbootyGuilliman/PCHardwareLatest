package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IOrderDAO;
import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.dao.IUserDAO;
import epam.javalab22.pchardware.entity.Order;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.entity.User;
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

public class AdminService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        Locale locale = (Locale) session.getAttribute(LOCALE);
        IOrderDAO orderDAO = (IOrderDAO)context.getAttribute(ORDER_DAO);
        IProductDAO productDAO = (IProductDAO)context.getAttribute(PRODUCT_DAO);
        IUserDAO userDAO = (IUserDAO)context.getAttribute(USER_DAO);

        List<Order> listOfOrders = orderDAO.getAllOrders();
        List<Product> listOfProducts = productDAO.getAllProducts();
        List<User> listOfUsers = userDAO.getAllUsers();

        Iterator<Order> iterator = listOfOrders.iterator();
        while (iterator.hasNext()) {
            TimeAndDateHandler.setDate(iterator.next(), locale);
        }

        request.setAttribute(LIST_OF_ORDERS, listOfOrders);
        request.setAttribute(LIST_OF_PRODUCTS, listOfProducts);
        request.setAttribute(LIST_OF_USERS, listOfUsers);

        logger.traceExit();
        return ADMIN_JSP;
    }
}