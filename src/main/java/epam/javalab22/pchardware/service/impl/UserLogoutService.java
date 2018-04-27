package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static epam.javalab22.pchardware.util.Constant.*;

public class UserLogoutService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        try {
            request.logout();
        } catch (ServletException e) {
            logger.error(LOGOUT_ERROR_MESSAGE + e.getMessage());
        }

        logger.traceExit();
        return INDEX_JSP;
    }
}
