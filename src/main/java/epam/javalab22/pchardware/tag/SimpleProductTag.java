package epam.javalab22.pchardware.tag;

import epam.javalab22.pchardware.entity.Product;
import epam.javalab22.pchardware.util.CurrencyConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static epam.javalab22.pchardware.util.Constant.*;

public class SimpleProductTag extends SimpleTagSupport {

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void doTag() throws IOException {

        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        PageContext pageContext = (PageContext) jspContext;
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        Map<Product, Integer> shoppingCart = (ConcurrentHashMap<Product, Integer>) session.getAttribute(SHOPPING_CART);

        Locale locale = (Locale) session.getAttribute(LOCALE);
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(LOCALIZATION, locale);

        out.println("<div class='gallery'>");
        out.println("<div class='imgWrap'>");
        out.println("<a target='_blank' href='/Controller?service=product&id=" + product.getId() + "'>");
        out.println("<img src='resources/img/" + product.getImg() + "' alt='image'></a></div>");
        out.println("<div class='descName'>" + product.getName() + "</div>");
        out.println("<div class='desc'>");
        out.println(currency.format(CurrencyConverter.convert(product.getPrice(), locale)) + "</div>");

        if (!request.getRequestURI().contains(ORDER)) {
            out.println("<div class='descName'><form method='post' action='/Controller'>");
            out.println("<input type='hidden' name='shuffle' value='false'>");
            if (shoppingCart.containsKey(product)) {
                out.println("<input type='hidden' name='service' value='remove'>");
                out.println("<input type='hidden' name='id' value='" + product.getId() + "'>");
                out.println("<input type='hidden' name='referrer' value='" + request.getRequestURI() + "'>");
                out.println("<input type='submit' value='" + resourceBundle.getString(REMOVE) + "'/>");
            } else {
                out.println("<input type='hidden' name='service' value='add'>");
                out.println("<input type='hidden' name='id' value='" + product.getId() + "'>");
                out.println("<input type='hidden' name='referrer' value=" + request.getRequestURI() + ">");
                out.println("<input type='number' name='amount' min='1' max='10' value='1'/>");
                out.println("<input type='submit' value='" + resourceBundle.getString(ADD) + "'/>");
            }
            out.println("</form></div>");
        }
        out.println("</div>");
    }
}