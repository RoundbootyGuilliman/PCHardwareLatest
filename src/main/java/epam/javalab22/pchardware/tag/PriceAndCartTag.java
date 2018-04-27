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
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import static epam.javalab22.pchardware.util.Constant.*;

public class PriceAndCartTag extends SimpleTagSupport {

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
        Map<Product, Integer> shoppingCart = (ConcurrentHashMap) session.getAttribute(SHOPPING_CART);

        Locale locale = (Locale) session.getAttribute(LOCALE);
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(LOCALIZATION, locale);

        out.println("<div class='desc'>");
        out.println(currency.format(CurrencyConverter.convert(product.getPrice(), locale)) + "</div>");

        if (request.getRequestURI().contains(CART)) {
            out.println("<div class='desc'><form method='post' action='/Controller'>");
            out.println("<input type='hidden' name='service' value='update'>");
            out.println("<input type='hidden' name='id' value='" + product.getId() + "'>");
            out.println("<input type='hidden' name='referrer' value='/cart.jsp'>");
            out.println("<input type='number' name='amount' min='1' max='10' value='" + shoppingCart.get(product) + "'/>");
            out.println("<input type='submit' value='" + resourceBundle.getString(UPDATE) + "'/>");
            out.println("</form></div>");

            out.println("<div class='desc'><form method='post' action='/Controller'>");
            out.println("<input type='hidden' name='service' value='remove'>");
            out.println("<input type='hidden' name='id' value='" + product.getId() + "'>");
            out.println("<input type='hidden' name='referrer' value='/cart.jsp'>");
            out.println("<input type='submit' value='" + resourceBundle.getString(REMOVE) + "'/>");
            out.println("</form></div>");
        } else {
            out.println("<div class='desc'><form method='post' action='/Controller'>");
            if (shoppingCart.containsKey(product)) {
                out.println("<input type='hidden' name='service' value='remove'>");
                out.println("<input type='hidden' name='id' value='" + product.getId() + "'>");
                out.println("<input type='hidden' name='referrer' value='/Controller?" + request.getQueryString() + "'>");
                out.println("<input type='submit' value='" + resourceBundle.getString(REMOVE) + "'/>");
            } else {
                out.println("<input type='hidden' name='service' value='add'>");
                out.println("<input type='hidden' name='id' value='" + product.getId() + "'>");
                out.println("<input type='hidden' name='referrer' value='/Controller?" + request.getQueryString() + "'>");
                out.println("<input type='number' name='amount' min='1' max='10' value='1'/>");
                out.println("<input type='submit' value='" + resourceBundle.getString(ADD) + "'/>");
            }
            out.println("</form></div>");
        }
    }
}