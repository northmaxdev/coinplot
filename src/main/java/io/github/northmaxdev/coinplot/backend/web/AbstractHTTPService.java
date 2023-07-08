// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.web;

import io.github.northmaxdev.coinplot.backend.web.request.APIRequest;
import jakarta.annotation.Nonnull;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractHTTPService {

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(60);

    private final HttpClient httpClient;

    protected AbstractHTTPService(@Nonnull HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected final @Nonnull CompletableFuture<HttpResponse<byte[]>> sendRequest(@Nonnull APIRequest apiRequest) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(apiRequest.toURI())
                .timeout(TIMEOUT_DURATION)
                .build();

        return httpClient.sendAsync(httpRequest, BodyHandlers.ofByteArray());
    }
}
