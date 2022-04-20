package org.darkend.provider;

import org.darkend.converter.CurrencyConverter;

public class USD implements CurrencyConverter {

    @Override
    public double to(double sek) {
        return sek / 9.49;
    }

    @Override
    public double from(double usd) {
        return usd * 9.49;
    }

    @Override
    public String currency() {
        return "USD";
    }
}
