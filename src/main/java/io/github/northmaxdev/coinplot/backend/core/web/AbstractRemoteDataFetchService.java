// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractRemoteDataFetchService<R extends APIRequest, D, M> {

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(60);

    // External dependencies
    private final @Nonnull HttpClient httpClient;
    private final @Nonnull ObjectMapper jsonParser;
    private final @Nonnull DTOMapper<D, M> dtoMapper;

    // Internal
    private final @Nonnull Logger logger;
    private final List<R> requestHistory;

    protected AbstractRemoteDataFetchService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull DTOMapper<D, M> dtoMapper) {
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient is null");
        this.jsonParser = Objects.requireNonNull(jsonParser, "jsonParser is null");
        this.dtoMapper = Objects.requireNonNull(dtoMapper, "dtoMapper is null");

        logger = LoggerFactory.getLogger(getClass());
        requestHistory = new LinkedList<>(); // Rationale: most likely we'll have more writes than reads
    }

    public final List<R> getRequestHistory() {
        return requestHistory;
    }

    protected final @Nonnull M fetch(@Nonnull R apiRequest) throws FailedRemoteDataFetchException {
        Objects.requireNonNull(apiRequest, "apiRequest is null");
        HttpRequest httpRequest = apiRequest.toHTTPRequestBuilder()
                .GET()
                .timeout(TIMEOUT_DURATION)
                .build();

        try {
            HttpResponse<byte[]> response = httpClient.send(httpRequest, BodyHandlers.ofByteArray());

            int statusCode = response.statusCode();
            // While this might seem limited at first, every web service that follows industry conventions
            // and norms returns 200 OK on a successful response, and in any other case we're going to be
            // throwing an RFE anyway (even in the case of other 2XX codes), so might as well inline this here.
            if (statusCode != 200) {
                throw new FailedRemoteDataFetchException("Expected HTTP 200 OK, instead got: " + statusCode);
            }

            D dto = parseResponseBody(response.body(), jsonParser);
            M model = dtoMapper.map(dto);

            logger.info("Completed request: " + apiRequest);
            requestHistory.add(apiRequest);
            return model;
        } catch (IOException | InterruptedException | DTOMappingException e) {
            logger.error("Failed request: " + apiRequest + " (reason: " + e + ')');
            throw new FailedRemoteDataFetchException(e);
        }
    }

    protected abstract @Nonnull D parseResponseBody(byte[] responseBody, @Nonnull ObjectMapper jsonParser) throws IOException;
}
