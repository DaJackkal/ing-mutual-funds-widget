package com.personal.mutualfunds.util;

import java.text.DecimalFormat;

public final class MoneyUtil {

    public static final String FOUR_DECIMALS = "#.####";
    public static final String TWO_DECIMALS = "#.##";

    private MoneyUtil(){}

    public static String formatAmount(final double amount, final String format){
        return new DecimalFormat(format).format(amount);
    }

}
