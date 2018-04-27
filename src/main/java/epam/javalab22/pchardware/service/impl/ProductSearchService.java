package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static epam.javalab22.pchardware.util.Constant.*;

public class ProductSearchService implements BasicService {

    private static final int MAX_PRICE_THRESHOLD = 400000;
    private static final int MIN_PRICE_THRESHOLD = 0;

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();
        IProductDAO productDAO = (IProductDAO) context.getAttribute(PRODUCT_DAO);

        List<Product> listOfProducts = productDAO.findByType(request.getParameter(TYPE), session.getAttribute(LOCALE).toString());
        Set<String> setOfVendors = new HashSet<>();

        for (Product product : listOfProducts) {
            setOfVendors.add(product.getVendor());
        }

        if (request.getParameter(SEARCH_PARAMS) != null) {
            listOfProducts = filterProducts(listOfProducts, request);
        }

        request.setAttribute(LIST_OF_PRODUCTS, listOfProducts);
        request.setAttribute(SET_OF_VENDORS, setOfVendors);

        logger.traceExit();
        return SEARCH_JSP;
    }

    private List<Product> filterProducts(List<Product> listOfProducts, HttpServletRequest request) {

        List<Product> priceWasteList = new ArrayList<>();
        List<Product> vendorWasteList = new ArrayList<>();

        int maxPrice = setPriceRange(request.getParameter(MAX_PRICE), true);
        int minPrice = setPriceRange(request.getParameter(MIN_PRICE), false);

        String vendor;

        for (Product product : listOfProducts) {
            vendor = product.getVendor();

            if (request.getParameter(vendor) == null) {
                vendorWasteList.add(product);
            }
            if (product.getPrice() > maxPrice || product.getPrice() < minPrice) {
                priceWasteList.add(product);
            }
        }
        if (listOfProducts.size() > vendorWasteList.size()) {
            listOfProducts.removeAll(vendorWasteList);
        }
        listOfProducts.removeAll(priceWasteList);
        return listOfProducts;
    }

    private int setPriceRange(String price, boolean max) {
        if (price.equals(EMPTY) && max) {
            return MAX_PRICE_THRESHOLD;
        } else if (price.equals(EMPTY) && !max) {
            return MIN_PRICE_THRESHOLD;
        } else {
            return Integer.parseInt(price);
        }
    }
}