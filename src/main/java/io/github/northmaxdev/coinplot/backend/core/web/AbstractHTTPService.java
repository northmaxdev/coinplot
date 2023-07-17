// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.ResourceFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;

public abstract class AbstractHTTPService<R extends APIRequest, D, M> {

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(60);

    private final HttpClient httpClient;
    private final ObjectMapper jsonParser;
    private final DTOMapper<D, M> dtoMapper;
    private final Logger logger;

    protected AbstractHTTPService(HttpClient httpClient, ObjectMapper jsonParser, DTOMapper<D, M> dtoMapper) {
        this.httpClient = httpClient;
        this.jsonParser = jsonParser;
        this.dtoMapper = dtoMapper;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    protected final M fetch(@Nonnull R apiRequest) throws ResourceFetchException {
        URI uri = apiRequest.getURI();
        Map<String, String> headers = apiRequest.getHeaders();

        var builder = HttpRequest.newBuilder();
        headers.forEach(builder::setHeader);

        HttpRequest httpRequest = builder.GET()
                .uri(uri)
                .timeout(TIMEOUT_DURATION)
                .build();

        try {
            HttpResponse<byte[]> response = httpClient.send(httpRequest, BodyHandlers.ofByteArray());

            int statusCode = response.statusCode();
            // While this might seem limited at first, every web service that follows industry conventions and norms
            // returns 200 OK on a successful response, and in any other case we're going to be throwing an RFE anyway
            // (even in the case of other 2XX codes), so might as well inline this here.
            if (statusCode != 200) {
                throw new ResourceFetchException("Expected HTTP 200 OK, instead got: " + statusCode);
            }

            D dto = parseResponseBody(response.body(), jsonParser);
            M model = dtoMapper.map(dto);

            logger.info("Completed request: " + apiRequest);
            return model;
        } catch (IOException | InterruptedException | DTOMappingException e) {
            throw new ResourceFetchException(e);
        }
    }

    protected abstract D parseResponseBody(byte[] responseBody, ObjectMapper jsonParser) throws IOException;
}
