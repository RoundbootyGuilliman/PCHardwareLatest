package epam.javalab22.pchardware.tag;

import epam.javalab22.pchardware.entity.Product;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.*;

public class ComplexProductTag extends SimpleTagSupport {

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void doTag() throws JspException, IOException {

        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        Iterator<Map.Entry<String, String>> iterator = product.getMapOfCharacteristics().entrySet().iterator();
        iterator.next();

        out.println("<div class='product'>");
        out.println("<div class='image'>");
        out.println("<a target='_blank' href='/Controller?service=product&id=" + product.getId() + "'>");
        out.println("<img class='searchImg' src='resources/img/" + product.getImg() + "' alt='image'></div>");

        out.println("<div class='center'>");
        out.println("<div class='desc'>" + product.getName() + "</div></a>");

        out.println("<table class='searchTable'>");
        for(int i = 0; i < 2; i ++) {
            out.println("<tr>");
            for(int j = 0; j < 3; j ++) {
                Map.Entry<String, String> entry = iterator.next();
                out.println("<td>");
                out.println(entry.getKey() + ": " + entry.getValue());
                out.println("</td>");
            }
            out.println("</tr>");
        }
        out.println("</table></div>");
        out.println("<div class='right'>");

        getJspBody().invoke(null);

        out.println("</div></div>");
    }
}