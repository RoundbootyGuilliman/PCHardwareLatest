package epam.javalab22.pchardware.util;

import epam.javalab22.pchardware.service.BasicService;
import epam.javalab22.pchardware.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static epam.javalab22.pchardware.util.Constant.*;

public class ServiceInitializer {

    private ServiceInitializer(){}

    private static Logger logger = LogManager.getLogger(ServiceInitializer.class);

    public static Map<String, BasicService> initializeServices() {
        logger.traceEntry();
        Map<String, BasicService> mapOfServices = new HashMap<>();

        mapOfServices.put(LOGIN, new UserLoginService());
        mapOfServices.put(DELETE_PRODUCT, new ProductDeleteService());
        mapOfServices.put(ORDER_STATUS, new OrderStatusService());
        mapOfServices.put(DELETE_USER, new UserDeleteService());
        mapOfServices.put(ADMIN, new AdminService());
        mapOfServices.put(ADD_PRODUCT, new ProductAddService());
        mapOfServices.put(LOGOUT, new UserLogoutService());
        mapOfServices.put(UPDATE, new CartUpdateService());
        mapOfServices.put(REVIEW, new ReviewAddService());
        mapOfServices.put(REGISTER, new UserRegisterService());
        mapOfServices.put(EDIT, new UserEditService());
        mapOfServices.put(PRODUCT, new ProductShowService());
        mapOfServices.put(SEARCH, new ProductSearchService());
        mapOfServices.put(CHANGE_LOCALE, new LocaleChangeService());
        mapOfServices.put(ADD, new CartAddService());
        mapOfServices.put(REMOVE, new CartRemoveService());
        mapOfServices.put(CHECKOUT, new CartCheckoutService());
        logger.traceExit();
        return mapOfServices;
    }
}
