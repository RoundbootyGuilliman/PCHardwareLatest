package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IProductDAO;

import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static epam.javalab22.pchardware.util.Constant.*;

public class ProductDeleteService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        ServletContext context = request.getServletContext();

        IProductDAO productDAO = (IProductDAO)context.getAttribute(PRODUCT_DAO);
        int id = Integer.parseInt(request.getParameter(ID));
        productDAO.deleteProduct(id);

        logger.traceExit();
        return REDIRECT_JSP;
    }
}