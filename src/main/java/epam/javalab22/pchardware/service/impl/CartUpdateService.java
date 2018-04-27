package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static epam.javalab22.pchardware.util.Constant.*;

public class CartUpdateService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        HttpSession session = request.getSession();

        Map<Product, Integer> shoppingCart = (ConcurrentHashMap<Product, Integer>) session.getAttribute(SHOPPING_CART);
        int id = Integer.parseInt(request.getParameter(ID));
        int amount = Integer.parseInt(request.getParameter(AMOUNT));

        Iterator<Map.Entry<Product, Integer>> iterator = shoppingCart.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Product, Integer> entry = iterator.next();
            if (entry.getKey().getId() == id) {
                entry.setValue(amount);
            }
        }
        session.setAttribute(SHOPPING_CART, shoppingCart);

        logger.traceExit();
        return REDIRECT_JSP;
    }
}