package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IOrderDAO;
import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static epam.javalab22.pchardware.util.Constant.*;

public class OrderStatusService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        ServletContext context = request.getServletContext();
        IOrderDAO orderDAO = (IOrderDAO)context.getAttribute(ORDER_DAO);
        String status = request.getParameter(STATUS);
        int id = Integer.parseInt(request.getParameter(ID));

        if (status != null) orderDAO.changeOrderStatus(status, id);

        logger.traceExit();
        return REDIRECT_JSP;
    }
}
