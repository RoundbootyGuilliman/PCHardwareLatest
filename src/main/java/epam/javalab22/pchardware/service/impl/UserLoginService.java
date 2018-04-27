package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Locale;
import java.util.ResourceBundle;

import static epam.javalab22.pchardware.util.Constant.*;

public class UserLoginService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(LOCALIZATION, locale);

        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        try {
            request.login(username, password);
        } catch (ServletException e) {
            logger.error(LOGIN_ERROR_MESSAGE + e.getMessage());
            request.setAttribute(LOGIN_ERROR, resourceBundle.getString(LOGIN_ERROR));
            logger.traceExit();
            return LOGIN_JSP;
        }

        logger.traceExit();
        return REDIRECT_JSP;
    }
}
