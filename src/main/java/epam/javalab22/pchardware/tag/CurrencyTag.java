package epam.javalab22.pchardware.tag;

import epam.javalab22.pchardware.util.CurrencyConverter;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import static epam.javalab22.pchardware.util.Constant.*;

public class CurrencyTag extends SimpleTagSupport {

    private float figure;

    public void setFigure(float figure) {
        this.figure = figure;
    }

    @Override
    public void doTag() throws IOException {

        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        PageContext pageContext = (PageContext) jspContext;
        HttpSession session = pageContext.getSession();

        Locale locale = (Locale) session.getAttribute(LOCALE);
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);

        out.println(currency.format(CurrencyConverter.convert(figure, locale)));
    }
}