// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONMapper;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Objects;

public abstract class AbstractRemoteDataFetchService<R extends APIRequest, D, M> {

    private static final Duration HTTP_REQUEST_TIMEOUT_DURATION = Duration.ofSeconds(60);
    private static final int HTTP_OK = 200;

    private final @Nonnull HttpClient httpClient;
    private final @Nonnull ObjectMapper jsonParser;
    private final @Nonnull JSONMapper<D> jsonMapper;
    private final @Nonnull DTOMapper<D, M> dtoMapper;
    private final @Nonnull Logger logger;

    protected AbstractRemoteDataFetchService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull JSONMapper<D> jsonMapper,
            @Nonnull DTOMapper<D, M> dtoMapper) {
        this.httpClient = Objects.requireNonNull(httpClient);
        this.jsonParser = Objects.requireNonNull(jsonParser);
        this.jsonMapper = Objects.requireNonNull(jsonMapper);
        this.dtoMapper = Objects.requireNonNull(dtoMapper);
        logger = LoggerFactory.getLogger(getClass());
    }

    protected final @Nonnull Logger getLogger() {
        return logger;
    }

    protected final @Nonnull M fetch(@Nonnull R apiRequest) throws FailedRemoteDataFetchException {
        HttpRequest httpRequest = Objects.requireNonNull(apiRequest)
                .toHTTPRequestBuilder()
                .timeout(HTTP_REQUEST_TIMEOUT_DURATION)
                .GET()
                .build();

        try {
            HttpResponse<byte[]> response = httpClient.send(httpRequest, BodyHandlers.ofByteArray());

            int statusCode = response.statusCode();
            // While this might seem limited at first, every web service that follows industry conventions
            // and norms returns 200 OK on a successful response, and in any other case we're going to be
            // throwing an RFE anyway (even in the case of other 2XX codes), so might as well inline this here.
            if (statusCode != HTTP_OK) {
                throw new FailedRemoteDataFetchException("Expected HTTP 200 OK, instead got: " + statusCode);
            }

            D dto = jsonMapper.map(response.body(), jsonParser);
            M model = dtoMapper.map(dto);

            logger.info("Completed request: {}", apiRequest);
            return model;
        } catch (IOException | InterruptedException | DTOMappingException e) {
            logger.error("Failed request: {}", apiRequest, e); // https://www.slf4j.org/faq.html#paramException
            throw new FailedRemoteDataFetchException(e);
        }
    }
}
