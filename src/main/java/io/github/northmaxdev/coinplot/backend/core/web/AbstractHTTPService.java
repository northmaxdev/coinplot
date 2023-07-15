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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public abstract class AbstractHTTPService<R extends APIRequest, D, M> {

    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(60);

    private final HttpClient httpClient;
    private final ObjectMapper jsonParser;
    private final DTOMapper<D, M> dtoMapper;
    private final Logger logger;

    protected AbstractHTTPService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            DTOMapper<D, M> dtoMapper) {
        this.httpClient = httpClient;
        this.jsonParser = jsonParser;
        this.dtoMapper = dtoMapper;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    protected final M fetch(@Nonnull R apiRequest) throws ResourceFetchException {
        HttpResponse<byte[]> responsePreStatusCheck = sendAPIRequest(apiRequest);
        HttpResponse<byte[]> responsePostStatusCheck = checkStatusCode(responsePreStatusCheck);
        D dto = mapResponseBodyToDTO(responsePostStatusCheck.body(), jsonParser);
        M resource = mapDTOToModel(dto);

        logger.info("Fetched: " + apiRequest);
        return resource;
    }

    protected abstract HttpResponse<byte[]> checkStatusCode(HttpResponse<byte[]> httpResponse)
            throws ResourceFetchException;

    protected abstract D mapResponseBodyToDTO(byte[] responseBody, ObjectMapper jsonParser)
            throws ResourceFetchException;

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

    private M mapDTOToModel(D dto) throws ResourceFetchException {
        try {
            return dtoMapper.map(dto);
        } catch (DTOMappingException e) {
            throw new ResourceFetchException(e);
        }
    }
}
