package epam.javalab22.pchardware.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static epam.javalab22.pchardware.util.Constant.*;

public class MD5Encoder {

    private MD5Encoder(){}

    private static Logger logger = LogManager.getLogger(MD5Encoder.class);

    public static String encode(String base) {
        logger.traceEntry();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            logger.error(NO_SUCH_ALGORITHM_MESSAGE + e.getMessage());
        }
        md5.update(StandardCharsets.UTF_8.encode(base));
        logger.traceExit();
        return String.format(FORMAT_032X, new BigInteger(1, md5.digest()));
    }
}
