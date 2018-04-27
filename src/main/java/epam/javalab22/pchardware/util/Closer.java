package epam.javalab22.pchardware.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.javalab22.pchardware.util.Constant.*;

public class Closer {

    private static Logger logger = LogManager.getLogger(Closer.class);

    private Closer(){}

    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            logger.error(CLOSE_RESULTSET_MESSAGE + e.getMessage());
        }
    }
}
