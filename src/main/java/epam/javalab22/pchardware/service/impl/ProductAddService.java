package epam.javalab22.pchardware.service.impl;

import epam.javalab22.pchardware.dao.IProductDAO;
import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.service.BasicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static epam.javalab22.pchardware.util.Constant.*;

public class ProductAddService implements BasicService {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public String execute(HttpServletRequest request) {
        logger.traceEntry();

        ServletContext context = request.getServletContext();
        IProductDAO productDAO = (IProductDAO)context.getAttribute(PRODUCT_DAO);

        String name = request.getParameter(NAME);
        int price = Integer.parseInt(request.getParameter(PRICE));
        String type = request.getParameter(TYPE);
        String img = uploadFile(request);
        String vendor = request.getParameter(VENDOR);

        String charsEng = request.getParameter(CHARS_ENG);
        String charsRus = request.getParameter(CHARS_RUS);

        Product product = new Product(ID_PLACEHOLDER, name, price, type, img);

        Map<String, String> mapOfCharsEng = new LinkedHashMap<> ();
        Map<String, String> mapOfCharsRus = new LinkedHashMap<>();
        mapOfCharsEng.put(VENDOR, vendor);
        mapOfCharsRus.put(VENDOR_RUS, vendor);

        for (String line : charsEng.split(LINE_BREAK_REGEX)) {
            String[] tokens = line.split(COLON);
            mapOfCharsEng.put(tokens[0], tokens[1]);
        }
        for (String line : charsRus.split(LINE_BREAK_REGEX)) {
            String[] tokens = line.split(COLON);
            mapOfCharsRus.put(tokens[0], tokens[1]);
        }

        productDAO.addProduct(product, mapOfCharsEng, mapOfCharsRus);

        logger.traceExit();
        return REDIRECT_JSP;
    }

    private String uploadFile(HttpServletRequest request) {
        logger.traceEntry();

        String applicationPath = request.getServletContext().getRealPath(EMPTY);
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String fileName = null;


        try {
            for (Part part : request.getParts()) {
                if (part.getHeader(CONTENT_DISPOSITION).contains(FILENAME)) {
                    fileName = getFileName(part);
                    part.write(uploadFilePath + File.separator + fileName);
                }
            }
        } catch (ServletException e) {
            logger.error(GETPARTS_MESSAGE + e.getMessage());
        } catch (IOException e) {
            logger.error(PART_WRITE_MESSAGE + e.getMessage());
        }
        logger.traceExit(fileName);
        return fileName;
    }

    private String getFileName(Part part) {
        logger.traceEntry();

        String contentDisp = part.getHeader(CONTENT_DISPOSITION);
        String[] tokens = contentDisp.split(SEMICOLON);
        for (String token : tokens) {
            if (token.trim().startsWith(FILENAME)) {
                logger.traceExit();
                return token.substring(token.indexOf(EQUALS) + 2, token.length() - 1);
            }
        }

        logger.traceExit();
        return EMPTY;
    }
}