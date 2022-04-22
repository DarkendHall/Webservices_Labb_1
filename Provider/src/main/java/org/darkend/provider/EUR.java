package org.darkend.provider;

import org.darkend.annotation.Currency;
import org.darkend.converter.CurrencyConverter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Currency("EUR")
public class EUR implements CurrencyConverter {

    private final Double currentRateToEUR;
    private final Double currentRateFromEUR;

    public EUR() {
        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(URI.create(
                            "https://free.currconv.com/api/v7/convert?q=SEK_EUR&compact=ultra&apiKey=" + System.getenv(
                                    "API_KEY")))
                    .GET()
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
            var responseStringArray = response.split(":");
            var responseNumberAsString = responseStringArray[1];
            currentRateToEUR = Double.parseDouble(responseNumberAsString.replace("}", ""));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(URI.create(
                            "https://free.currconv.com/api/v7/convert?q=EUR_SEK&compact=ultra&apiKey=" + System.getenv(
                                    "API_KEY")))
                    .GET()
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
            var responseStringArray = response.split(":");
            var responseNumberAsString = responseStringArray[1];
            currentRateFromEUR = Double.parseDouble(responseNumberAsString.replace("}", ""));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double convertTo(double sek) {
        return sek * currentRateToEUR;
    }

    @Override
    public double convertFrom(double eur) {
        return eur * currentRateFromEUR;
    }

    @Override
    @Deprecated
    public String currency() {
        return "EUR";
    }
}
