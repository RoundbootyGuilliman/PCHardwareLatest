package epam.javalab22.pchardware.controller;

import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static epam.javalab22.pchardware.util.Constant.*;

public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.traceEntry();
        LOGGER.traceExit();
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.traceEntry();

        ServletContext servletContext = request.getServletContext();
        String parameter = request.getParameter(SERVICE);
        LOGGER.debug(SERVICE_PARAM, parameter);

        Map<String, BasicService> mapOfServices = (Map<String, BasicService>) servletContext.getAttribute(MAP_OF_SERVICES);
        BasicService service = mapOfServices.get(parameter);
        LOGGER.debug(SERVICE_IS, service.getClass().getSimpleName());

        String path = service.execute(request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        LOGGER.traceExit(path);
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.error(DISPATCHER_MESSAGE + e.getMessage());
        }
    }
}
