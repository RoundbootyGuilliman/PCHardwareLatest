package epam.javalab22.pchardware.filter;

import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import static epam.javalab22.pchardware.util.Constant.*;

public class MainPageFilter implements Filter {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.traceEntry(SETTING_PRODUCTS);

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        ServletContext context = request.getServletContext();

        IProductDAO productDAO = (IProductDAO)context.getAttribute(PRODUCT_DAO);
        List<Product> allProducts = productDAO.getAllProducts();
        List<Product> listOfProducts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listOfProducts.add(allProducts.get(i));
        }
        request.setAttribute(LIST_OF_PRODUCTS, listOfProducts);

        logger.traceExit();
        filterChain.doFilter(servletRequest, servletResponse);
    }
}