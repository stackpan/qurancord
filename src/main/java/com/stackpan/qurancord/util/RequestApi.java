package com.stackpan.qurancord.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class RequestApi {

    public static String fetchData(HttpClient httpClient, String uri) {
        return httpClient.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .timeout(Duration.ofMinutes(1))
                        .GET()
                        .build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
    }

}
