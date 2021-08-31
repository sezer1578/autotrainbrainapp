package com.hms.atbotizmozel.util;

import org.apache.commons.math3.util.Precision;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Batuhan on 17.01.2018.
 */

public class PriceUtils {

    private static DecimalFormat defaultDecimalFormat;

    private static DecimalFormat getDecimalFormat() {
        if (defaultDecimalFormat == null) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
            decimalFormatSymbols.setDecimalSeparator(',');
            defaultDecimalFormat = new DecimalFormat("###0.00", decimalFormatSymbols);
            defaultDecimalFormat.setGroupingUsed(true);
        }
        return defaultDecimalFormat;
    }

    public static String formatted(float val) {
        return getDecimalFormat().format(round(val));
    }

    public static float round(float price) {
        return Precision.round(price, 2, BigDecimal.ROUND_HALF_UP);
    }

}
