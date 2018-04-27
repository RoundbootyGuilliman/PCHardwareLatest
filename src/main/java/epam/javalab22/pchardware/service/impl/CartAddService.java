package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static epam.javalab22.pchardware.util.Constant.*;

public class CartAddService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        IProductDAO productDAO = (IProductDAO)context.getAttribute(PRODUCT_DAO);

        Map<Product, Integer> shoppingCart = (ConcurrentHashMap<Product, Integer>) session.getAttribute(SHOPPING_CART);
        int id = Integer.parseInt(request.getParameter(ID));
        int amount = Integer.parseInt(request.getParameter(AMOUNT));
        Locale locale = (Locale) session.getAttribute(LOCALE);

        Product product = productDAO.findById(id, locale.toString());
        shoppingCart.put(product, amount);
        session.setAttribute(SHOPPING_CART, shoppingCart);
        request.setAttribute(PRODUCT, product);

        logger.traceExit();
        return REDIRECT_JSP;
    }
}