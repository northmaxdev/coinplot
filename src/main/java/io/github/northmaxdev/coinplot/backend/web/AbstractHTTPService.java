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

public abstract class AbstractHTTPService<R extends APIRequest> {

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(60);

    private final HttpClient httpClient;
    // TODO:
    //  Add support for "API request history". Basically a mutable List<R> with a final getter that gets updated right
    //  between constructing an HTTP request and sending it.

    protected AbstractHTTPService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected final @Nonnull CompletableFuture<HttpResponse<byte[]>> sendRequest(@Nonnull R apiRequest) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(apiRequest.toURI())
                .timeout(TIMEOUT_DURATION)
                .build();

        return httpClient.sendAsync(httpRequest, BodyHandlers.ofByteArray());
    }
}
