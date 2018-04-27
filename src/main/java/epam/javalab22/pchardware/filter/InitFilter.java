package epam.javalab22.pchardware.filter;

import epam.javalab22.pchardware.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import static epam.javalab22.pchardware.util.Constant.*;

public class InitFilter implements Filter {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        if (session.getAttribute(SHOPPING_CART) == null) {
            session.setAttribute(SHOPPING_CART, new ConcurrentHashMap<Product, Integer>());
            logger.info(SETTING_CART);
        }

        if (session.getAttribute(LOCALE) == null) {
            Locale userPrefLocale = new Locale(RU, KZ);
            if (request.getLocale().toString().contains(EN)) {
                userPrefLocale = new Locale(EN, US);
            }
            logger.info(SETTING_LOCALE, userPrefLocale.toString());
            session.setAttribute(LOCALE, userPrefLocale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
