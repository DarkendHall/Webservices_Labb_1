package org.darkend;

import org.darkend.converter.CurrencyConverter;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceLoader<CurrencyConverter> serviceLoader = ServiceLoader.load(
                CurrencyConverter.class);

        double standardAmount = 100;

        System.out.println("From SEK with input of 100");
        serviceLoader.stream()
                .forEach(c -> System.out.printf("%.2f %s \r\n", c.get()
                        .to(standardAmount), c.get()
                        .currency()));
        System.out.println("To SEK with input of 100");
        serviceLoader.stream()
                .forEach(c -> System.out.printf("%.2f (from %s) \r\n", c.get()
                        .from(standardAmount), c.get()
                        .currency()));
    }
}