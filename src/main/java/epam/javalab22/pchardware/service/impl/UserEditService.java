package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IUserDAO;
import epam.javalab22.pchardware.entity.User;
import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static epam.javalab22.pchardware.util.Constant.*;

public class UserEditService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        ServletContext context = request.getServletContext();

        IUserDAO userDAO = (IUserDAO)context.getAttribute(USER_DAO);
        String username = request.getParameter(USERNAME);
        String email = request.getParameter(EMAIL);
        String name = request.getParameter(NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String address = request.getParameter(ADDRESS);

        User user = new User(username, email, null, name, lastName, phoneNumber, address);

        userDAO.editUserData(user);

        logger.traceExit();
        return REDIRECT_JSP;
    }
}
