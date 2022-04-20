package org.darkend.converter;

public interface CurrencyConverter {

    double to(double sek);

    double from(double outputCurrency);
}
