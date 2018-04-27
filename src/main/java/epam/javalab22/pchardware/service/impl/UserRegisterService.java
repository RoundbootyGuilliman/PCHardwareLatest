package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IUserDAO;
import epam.javalab22.pchardware.entity.User;
import epam.javalab22.pchardware.service.BasicService;
import epam.javalab22.pchardware.util.MD5Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static epam.javalab22.pchardware.util.Constant.*;

public class UserRegisterService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        ServletContext context = request.getServletContext();

        String username = request.getParameter(USERNAME);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String address = request.getParameter(ADDRESS);

        Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

        Matcher usernameMatcher = usernamePattern.matcher(username);
        Matcher emailMatcher = emailPattern.matcher(email);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        if (usernameMatcher.matches() && emailMatcher.matches() && passwordMatcher.matches()) {
            password = MD5Encoder.encode(password);
            IUserDAO userDAO = (IUserDAO)context.getAttribute(USER_DAO);
            User user = new User(username, email, password, name, lastName, phoneNumber, address);
            userDAO.registerUser(user);

            logger.traceExit();
            return REDIRECT_JSP;
        } else {
            logger.error(NO_MATCH);
            request.setAttribute(MESSAGE, NO_MATCH);
            return ERROR_JSP;
        }
    }
}