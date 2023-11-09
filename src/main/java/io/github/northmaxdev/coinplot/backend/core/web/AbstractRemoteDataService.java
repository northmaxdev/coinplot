// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONParsingStrategy;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Objects;

public abstract class AbstractRemoteDataService<T, R extends APIRequest, D> {

    private static final Duration HTTP_REQUEST_TIMEOUT_DURATION = Duration.ofSeconds(60);

    private final @Nonnull HttpClient httpClient;
    private final @Nonnull ObjectMapper jsonParser;
    private final @Nonnull JSONParsingStrategy<D> jsonParsingStrategy;
    private final @Nonnull DTOMapper<D, T> dtoMapper;
    private final @Nonnull Logger logger;

    protected AbstractRemoteDataService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull JSONParsingStrategy<D> jsonParsingStrategy,
            @Nonnull DTOMapper<D, T> dtoMapper) {
        this.httpClient = Objects.requireNonNull(httpClient);
        this.jsonParser = Objects.requireNonNull(jsonParser);
        this.jsonParsingStrategy = Objects.requireNonNull(jsonParsingStrategy);
        this.dtoMapper = Objects.requireNonNull(dtoMapper);
        logger = LoggerFactory.getLogger(getClass());
    }

    protected final @Nonnull Logger getLogger() {
        return logger;
    }

    protected final @Nonnull T fetch(@Nonnull R apiRequest) {
        HttpRequest httpRequest = Objects.requireNonNull(apiRequest)
                .toHTTPRequestBuilder()
                .timeout(HTTP_REQUEST_TIMEOUT_DURATION)
                .GET()
                .build();

        try {
            HttpResponse<byte[]> response = httpClient.send(httpRequest, BodyHandlers.ofByteArray());

            int statusCode = response.statusCode();
            if (!isStatusCodeAcceptable(statusCode)) {
                logger.error("Unacceptable status code: {}, aborting request", statusCode);
                return fallbackData();
            }

            D dto = jsonParsingStrategy.apply(response.body(), jsonParser);
            T data = dtoMapper.map(dto);

            logger.info("Completed request: {}", apiRequest);
            return data;
        } catch (IOException e) {
            // See the following page for more info on why toString() is explicitly called on the exception:
            // https://www.slf4j.org/faq.html#paramException
            logger.error("Request I/O failure: {}", e.toString());
            return fallbackData();
        } catch (InterruptedException e) {
            logger.error("Request interrupted");
            return fallbackData();
        }
    }

    /////////////////////////////////////
    // Subclass Behavior Customization //
    /////////////////////////////////////

    protected abstract @Nonnull T fallbackData();

    // Non-final. The primitive implementation below should generally suffice.
    // Override for custom HTTP status code validation logic.
    protected boolean isStatusCodeAcceptable(int statusCode) {
        return statusCode == HttpStatus.OK.value();
    }
}
