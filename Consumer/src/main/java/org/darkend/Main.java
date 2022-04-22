package org.darkend;

import org.darkend.annotation.Currency;
import org.darkend.converter.CurrencyConverter;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceLoader<CurrencyConverter> serviceLoader = ServiceLoader.load(CurrencyConverter.class);

        double standardAmount = 100;

        var loadedServices = serviceLoader.stream()
                .map(ServiceLoader.Provider::get)
                .toList();

        loadedServices.forEach(CurrencyConverter::getCurrentRates);

        System.out.println("From SEK with input of 100");

        loadedServices.forEach(
                c -> System.out.printf("%.2f %s \r\n", c.convertTo(standardAmount), getAnnotationValue(c)));

        System.out.println("To SEK with input of 100");

        loadedServices.forEach(
                c -> System.out.printf("%.2f (from %s) \r\n", c.convertFrom(standardAmount), getAnnotationValue(c)));
    }

    private static String getAnnotationValue(CurrencyConverter currencyConverter) {
        try {
            return currencyConverter.getClass()
                    .getAnnotation(Currency.class)
                    .value();
        } catch (NullPointerException e) {
            throw new NoValueFoundException("Couldn't find a value for this currency, " + currencyConverter.getClass()
                    .getSimpleName());
        }
    }
}

class NoValueFoundException extends RuntimeException {
    public NoValueFoundException(String message) {
        super(message);
    }
}
