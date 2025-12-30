package com.diro.ift2255.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApi {

    private final HttpClient client;

    public HttpClientApi() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Envoie un GET HTTP et retourne le body en String.
     */
    public String get(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP " + response.statusCode()
                        + " pour l'URL " + url);
            }

            return response.body();

        } catch (Exception e) {
            throw new RuntimeException("Erreur d'appel HTTP vers " + url, e);
        }
    }
}
