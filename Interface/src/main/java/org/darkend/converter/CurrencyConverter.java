package org.darkend.converter;

public interface CurrencyConverter {

    double convertTo(double sek);

    double convertFrom(double outputCurrency);

    String currency();
}
