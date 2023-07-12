package com.bestbuy.ecommerce.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class UserUtils {
    public static String getUserEmailFromContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String formatToLocale(BigDecimal amount) {
        // Create a NumberFormat instance for the "en_NG" locale
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "NG"));

        // Cast the NumberFormat instance to a DecimalFormat instance
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;

        // Apply a custom pattern for formatting the currency amount
        decimalFormat.applyPattern("¤###,###.00");

        // Format the BigDecimal amount using the DecimalFormat
        String currencyString = decimalFormat.format(amount);

        // Create a DecimalFormatSymbols instance for the "en_NG" locale
        DecimalFormatSymbols symbol = DecimalFormatSymbols.getInstance(new Locale("en", "NG"));

        // Set the currency symbol to "N" (Naira)
        symbol.setCurrencySymbol("N");

        // Set the DecimalFormatSymbols for the DecimalFormat
        decimalFormat.setDecimalFormatSymbols(symbol);

        // Return the formatted currency string
        return currencyString;
    }

}