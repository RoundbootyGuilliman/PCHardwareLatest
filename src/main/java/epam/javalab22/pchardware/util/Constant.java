package epam.javalab22.pchardware.util;

public class Constant {

    private Constant() {}

    // General
    public static final String EMPTY = "";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String EQUALS = "=";
    public static final String LIST_OF_PRODUCTS = "listOfProducts";
    public static final String SHOPPING_CART = "shoppingCart";
    public static final String ID = "id";
    public static final String AMOUNT = "amount";
    public static final String USER = "user";
    public static final String USER_ROLE = "User";
    public static final String LIST_OF_ORDERS = "listOfOrders";
    public static final String PRICE = "price";
    public static final String TYPE = "type";
    public static final String VENDOR = "Vendor";
    public static final String VENDOR_RUS = "Производитель";
    public static final String CHARS_ENG = "charsEng";
    public static final String CHARS_RUS = "charsRus";
    public static final int ID_PLACEHOLDER = 0;
    public static final String SERVICE = "service";
    public static final String MAP_OF_SERVICES = "mapOfServices";
    public static final String SEARCH_PARAMS = "searchParams";
    public static final String MAX_PRICE = "maxPrice";
    public static final String MIN_PRICE = "minPrice";
    public static final String REVIEWS = "reviews";
    public static final String CART = "cart";
    public static final String ORDER = "order";
    public static final String UTF8 = "UTF-8";
    public static final String MD5 = "MD5";
    public static final String FORMAT_032X = "%032x";
    public static final String LIST_OF_USERS = "listOfUsers";
    public static final String SET_OF_VENDORS = "setOfVendors";
    public static final String STATUS = "status";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String USER_DAO = "userDao";
    public static final String ORDER_DAO = "orderDao";
    public static final String PRODUCT_DAO = "productDao";
    public static final String REVIEW_DAO = "reviewDao";
    public static final String MESSAGE = "message";
    public static final String ORDER_STATUS_PENDING = "Pending";
    public static final String LOCALE_PLACEHOLDER = "ru_KZ";
    public static final int LOCALE_ID_EN = 1;
    public static final int LOCALE_ID_RU = 2;

    // Regex
    public static final String LINE_BREAK_REGEX = "\\R";
    public static final String USERNAME_PATTERN = "[a-zA-Z0-9.-_]{3,}";
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}";
    public static final String PASSWORD_PATTERN = "[a-zA-Z0-9.-_]{5,}";

    // JSP pages
    public static final String ERROR_JSP = "error.jsp";
    public static final String INDEX_JSP = "index.jsp";
    public static final String ADMIN_JSP = "admin.jsp";
    public static final String SEARCH_JSP = "search.jsp";
    public static final String PRODUCT_JSP = "product.jsp";
    public static final String REDIRECT_JSP = "redirect.jsp";
    public static final String LOGIN_JSP = "login.jsp";

    // Locale
    public static final String LOCALE = "locale";
    public static final String RU = "ru";
    public static final String KZ = "KZ";
    public static final String EN = "en";
    public static final String US = "US";
    public static final String EN_US = "en_US";

    // File upload
    public static final String UPLOAD_DIR = "resources/img";
    public static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String FILENAME = "filename";

    // ResourceBundle keys
    public static final String LOCALIZATION = "l10n";
    public static final String OTHER = "other";
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String UPDATE = "update";
    public static final String KZT_TO_USD = "kztToUsd";
    public static final String DB_URL = "dbUrl";
    public static final String DB_USER = "dbUser";
    public static final String DB_PASS = "dbPassword";
    public static final String CONNS_TO_CREATE = "connectionsToCreate";
    public static final String LOGIN_ERROR = "loginError";

    // Service parameter keys
    public static final String ADD_PRODUCT = "addProduct";
    public static final String LOGOUT = "logout";
    public static final String REVIEW = "review";
    public static final String REGISTER = "register";
    public static final String EDIT = "edit";
    public static final String PRODUCT = "product";
    public static final String SEARCH = "search";
    public static final String CHANGE_LOCALE = "changeLocale";
    public static final String CHECKOUT = "checkout";
    public static final String ADMIN = "admin";
    public static final String DELETE_USER = "deleteUser";
    public static final String DELETE_PRODUCT = "deleteProduct";
    public static final String LOGIN = "login";

    // User fields
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ADDRESS = "address";

    // Logger messages
    public static final String SERVICE_PARAM = "Service parameter is {}";
    public static final String SERVICE_IS = "Service is {}";
    public static final String ORDERS_FOUND = "{} orders found";
    public static final String PLACING = "Placing {}'s order, sum is {}, time is {}, shoppingCart has {} products";
    public static final String INSERTING = "Inserting ordered product, order id is {}, product id is {}, amount is {}";
    public static final String SEARCH_ORD = "Looking for {}'s orders";
    public static final String SEARCH_ORD_PR = "Looking for order#{}'s products";
    public static final String PRODUCTS_FOUND = "{} products found";
    public static final String TYPE_SEARCH = "Looking for all {}s, locale is {}";
    public static final String SEARCH_PR = "Looking for product #{}, locale is {}";
    public static final String DELETING_PRODUCT = "Deleting product #{}";
    public static final String SEARCH_REVIEWS = "Looking for product #{}'s reviews";
    public static final String REVEWS_COUNT = "There are {} reviews";
    public static final String ADDING_REVIEW = "Adding {}'s review for product #{}, time is {}";
    public static final String SEARCH_USERS = "Looking for all users";
    public static final String USER_COUNT = "There are {} users";
    public static final String SEARCHING_ROLES = "Looking for {}'s roles";
    public static final String CHANGING_STATUS = "Changing order #{}'s status to {}";
    public static final String ADDING_PRODUCT = "Adding {}, map sizes are {} and {}";
    public static final String SEARCHING_DATA = "Looking for {}'s personal data";
    public static final String EDITING_DATA = "Editing {}'s personal data";
    public static final String REGISTERING = "Registering {}";
    public static final String ADDING_ROLE = "Adding {}'s roles";
    public static final String DELETING_USER = "Deleting {}";
    public static final String SETTING_CART = "Setting new shoppingCart object to Session";
    public static final String SETTING_LOCALE = "Setting {} as locale to session";
    public static final String SETTING_PRODUCTS = "Setting products to request for main page";
    public static final String SETTING_USER = "Setting user info and their order list to request";
    public static final String NO_MATCH = "User data does not match required patterns";
    public static final String MAP_INIT = "mapOfServices is initialized and set to servletContext";
    public static final String SETTING_DAO = "Setting DAO objects to ServletContext";
    public static final String CONN_INIT = "Connection pool initialized with {} connections";
    public static final String JDBC_DEREG = "Deregistering jdbc driver: {}";

    // Logger exception messages
    public static final String DEREG_MESSAGE = "DriverManager unable to deregister driver: ";
    public static final String DRIVER_REG_MESSAGE = "DriverManager unable to register driver: ";
    public static final String GETCONN_MESSAGE = "DriverManager unable to get connection: ";
    public static final String CLOSECONN_MESSAGE = "Connection unable to close: ";
    public static final String DISPATCHER_MESSAGE = "RequestDispatcher unable to forward the request: ";
    public static final String DB_ERROR_MESSAGE = "Error trying to access database: ";
    public static final String PREPARED_STAT_MESSAGE = "Error trying to prepare an SQLStatement: ";
    public static final String GETPARTS_MESSAGE = "Unable to get parts, request is not of type 'multipart/form-data': ";
    public static final String PART_WRITE_MESSAGE = "I/O error occurred during the retrieval of the parts: ";
    public static final String LOGIN_ERROR_MESSAGE = "Unable to login, wrong username/password of failed to access database: ";
    public static final String LOGOUT_ERROR_MESSAGE = "Unable to logout: ";
    public static final String CLOSE_RESULTSET_MESSAGE = "ResultSet unable to close: ";
    public static final String NO_SUCH_ALGORITHM_MESSAGE = "Can't get an instance of a MessageDigest implementation for specified algorithm: ";
}
