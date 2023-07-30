// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;

// Semantically not a @FunctionalInterface
public interface APIRequest {

    @Nonnull URI getURI();

    default Map<String, String> getHeaders() {
        return Map.of();
    }

    default HttpRequest.Builder toHTTPRequestBuilder() {
        URI uri = getURI();
        Map<String, String> headers = getHeaders();

        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
        headers.forEach(builder::setHeader);

        return builder;
    }
}
