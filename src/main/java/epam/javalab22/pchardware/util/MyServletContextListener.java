package epam.javalab22.pchardware.util;

import epam.javalab22.pchardware.connection.ConnectionPool;
import epam.javalab22.pchardware.dao.impl.OrderDAO;
import epam.javalab22.pchardware.dao.impl.ProductDAO;
import epam.javalab22.pchardware.dao.impl.ReviewDAO;
import epam.javalab22.pchardware.dao.impl.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import static epam.javalab22.pchardware.util.Constant.*;

public class MyServletContextListener implements ServletContextListener {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        logger.traceEntry();
        ServletContext context = contextEvent.getServletContext();

        context.setAttribute(MAP_OF_SERVICES, ServiceInitializer.initializeServices());
        logger.info(MAP_INIT);

        context.setAttribute(PRODUCT_DAO, new ProductDAO());
        context.setAttribute(ORDER_DAO, new OrderDAO());
        context.setAttribute(USER_DAO, new UserDAO());
        context.setAttribute(REVIEW_DAO, new ReviewDAO());
        logger.info(SETTING_DAO);

        logger.traceExit();
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        logger.traceEntry();
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        connectionPool.closeAllConnections();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(JDBC_DEREG, driver.getClass().getSimpleName());
            } catch (SQLException e) {
                logger.error(DEREG_MESSAGE + e.getMessage());
            }
        }
        logger.traceExit();
    }

}