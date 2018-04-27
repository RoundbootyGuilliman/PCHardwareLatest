package epam.javalab22.pchardware.filter;

import epam.javalab22.pchardware.dao.IOrderDAO;
import epam.javalab22.pchardware.dao.IUserDAO;
import epam.javalab22.pchardware.entity.Order;
import epam.javalab22.pchardware.entity.User;
import epam.javalab22.pchardware.util.TimeAndDateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static epam.javalab22.pchardware.util.Constant.*;

public class UserPageFilter implements Filter {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.traceEntry(SETTING_USER);

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        String username = request.getRemoteUser();
        Locale locale = (Locale) session.getAttribute(LOCALE);

        IUserDAO userDAO = (IUserDAO)context.getAttribute(USER_DAO);
        User user = userDAO.getUserByUsername(username);
        request.setAttribute(USER, user);

        IOrderDAO orderDAO = (IOrderDAO)context.getAttribute(ORDER_DAO);
        List<Order> orderList = orderDAO.findOrders(username);

        Iterator<Order> iterator = orderList.iterator();
        while (iterator.hasNext()) {
            TimeAndDateHandler.setDate(iterator.next(), locale);
        }

        request.setAttribute(LIST_OF_ORDERS, orderList);

        logger.traceExit();
        filterChain.doFilter(servletRequest, servletResponse);
    }
}