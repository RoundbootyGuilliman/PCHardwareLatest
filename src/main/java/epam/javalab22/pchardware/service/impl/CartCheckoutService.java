package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IOrderDAO;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.service.BasicService;
import epam.javalab22.pchardware.util.CurrencyConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static epam.javalab22.pchardware.util.Constant.*;

public class CartCheckoutService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        IOrderDAO orderDAO = (IOrderDAO)context.getAttribute(ORDER_DAO);

        String username = request.getRemoteUser();
        Locale locale = (Locale) session.getAttribute(LOCALE);
        Map<Product, Integer> shoppingCart = (ConcurrentHashMap<Product, Integer>) session.getAttribute(SHOPPING_CART);

        float sum = 0;
        String totalSum;

        for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
            sum += entry.getKey().getPrice() * entry.getValue();
        }
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        totalSum = currency.format(CurrencyConverter.convert(sum, locale));

        long time = Calendar.getInstance().getTimeInMillis();

        orderDAO.placeAnOrder(shoppingCart, username, totalSum, time);
        session.removeAttribute(SHOPPING_CART);

        logger.traceExit();
        return REDIRECT_JSP;
    }
}
