package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static epam.javalab22.pchardware.util.Constant.*;

public class LocaleChangeService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        HttpSession session = request.getSession();

        if (request.getParameter(LOCALE).equals(EN)) {
            session.setAttribute(LOCALE, new Locale(EN, US));
        } else {
            session.setAttribute(LOCALE, new Locale(RU, KZ));
        }

        logger.traceExit();
        return INDEX_JSP;
    }
}

