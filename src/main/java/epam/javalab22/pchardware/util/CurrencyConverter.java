package epam.javalab22.pchardware.util;

import java.util.Locale;
import java.util.ResourceBundle;

import static epam.javalab22.pchardware.util.Constant.*;

public class CurrencyConverter {

    private CurrencyConverter(){}

    private static final float KZT_USD = Float.valueOf(ResourceBundle.getBundle(OTHER).getString(KZT_TO_USD));

    public static float convert(float currency, Locale locale) {
        if (locale.toString().equals(EN_US)) return currency/KZT_USD;
        return currency;
    }
}
