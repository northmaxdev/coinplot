// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.ResourceFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public abstract class AbstractHTTPService<R extends APIRequest, D, M> {

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(60);

    private final HttpClient httpClient;
    private final ObjectMapper jsonParser;
    private final Logger logger;

    protected AbstractHTTPService(HttpClient httpClient, ObjectMapper jsonParser) {
        this.httpClient = httpClient;
        this.jsonParser = jsonParser;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    private HttpResponse<byte[]> sendAPIRequest(R apiRequest) throws ResourceFetchException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(apiRequest.toURI())
                .timeout(TIMEOUT_DURATION)
                .build();

        try {
            return httpClient.send(httpRequest, BodyHandlers.ofByteArray());
        } catch (IOException | InterruptedException e) {
            throw new ResourceFetchException(e);
        }
    }

    protected abstract HttpResponse<byte[]> checkStatusCode(HttpResponse<byte[]> httpResponse)
            throws ResourceFetchException;

    protected abstract D mapResponseBodyToDTO(byte[] responseBody, ObjectMapper jsonParser)
            throws ResourceFetchException;

    protected abstract M mapDTOToModel(D dto) throws ResourceFetchException;

    protected final M fetch(@Nonnull R apiRequest) throws ResourceFetchException {
        HttpResponse<byte[]> responsePreStatusCheck = sendAPIRequest(apiRequest);
        HttpResponse<byte[]> responsePostStatusCheck = checkStatusCode(responsePreStatusCheck);
        D dto = mapResponseBodyToDTO(responsePostStatusCheck.body(), jsonParser);
        M resource = mapDTOToModel(dto);

        logger.info("Fetched: " + apiRequest);
        return resource;
    }

    protected final Logger getLogger() {
        return logger;
    }
}
