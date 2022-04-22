package org.darkend.provider;

import org.darkend.annotation.Currency;
import org.darkend.converter.CurrencyConverter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Currency("USD")
public class USD implements CurrencyConverter {

    private Double currentRateToUSD;
    private Double currentRateFromUSD;

    @Override
    public double convertTo(double sek) {
        if (currentRateToUSD == null)
            throw new IllegalStateException("The rate is null, you need to get the rate, with getCurrentRates(), " +
                    "before using this method!");
        return sek * currentRateToUSD;
    }

    @Override
    public double convertFrom(double usd) {
        if (currentRateFromUSD == null)
            throw new IllegalStateException("The rate is null, you need to get the rate, with getCurrentRates(), " +
                    "before using this method!");
        return usd * currentRateFromUSD;
    }

    @Override
    @Deprecated
    public String currency() {
        return "USD";
    }

    @Override
    public void getCurrentRates() {
        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(URI.create(
                            "https://free.currconv.com/api/v7/convert?q=SEK_USD&compact=ultra&apiKey=" + System.getenv(
                                    "API_KEY")))
                    .GET()
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
            var responseStringArray = response.split(":");
            var responseNumberAsString = responseStringArray[1];
            currentRateToUSD = Double.parseDouble(responseNumberAsString.replace("}", ""));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(URI.create(
                            "https://free.currconv.com/api/v7/convert?q=USD_SEK&compact=ultra&apiKey=" + System.getenv(
                                    "API_KEY")))
                    .GET()
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
            var responseStringArray = response.split(":");
            var responseNumberAsString = responseStringArray[1];
            currentRateFromUSD = Double.parseDouble(responseNumberAsString.replace("}", ""));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
