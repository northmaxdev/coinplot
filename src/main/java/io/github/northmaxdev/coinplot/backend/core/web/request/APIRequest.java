// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;

public interface APIRequest {

    @Nonnull URI getURI();

    @Nonnull Map<String, String> getHeaders();

    default @Nonnull HttpRequest.Builder toHTTPRequestBuilder() {
        URI uri = getURI();
        Map<String, String> headers = getHeaders();

        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
        headers.forEach(builder::setHeader);

        return builder;
    }
}
