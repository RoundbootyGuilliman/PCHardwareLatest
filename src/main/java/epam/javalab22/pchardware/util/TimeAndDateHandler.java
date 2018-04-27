package epam.javalab22.pchardware.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeAndDateHandler {

    private TimeAndDateHandler(){}

    public static void setDate(TimeAndDateSupport object, Locale locale) {
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.LONG, locale);
        Date date = new Date(object.getTime());
        object.setDate(dateFormatter.format(date));
    }
}
