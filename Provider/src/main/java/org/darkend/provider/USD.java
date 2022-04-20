package org.darkend.provider;

import org.darkend.converter.CurrencyConverter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class USD implements CurrencyConverter {

    private final Double currentRateToUSD;
    private final Double currentRateFromUSD;

    public USD() {
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

    @Override
    public double convertTo(double sek) {
        return sek * currentRateToUSD;
    }

    @Override
    public double convertFrom(double usd) {
        return usd * currentRateFromUSD;
    }

    @Override
    public String currency() {
        return "USD";
    }
}
