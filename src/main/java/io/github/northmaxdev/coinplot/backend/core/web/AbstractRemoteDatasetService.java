// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONParsingStrategy;
import jakarta.annotation.Nonnull;

import java.net.http.HttpClient;
import java.util.Set;

public abstract class AbstractRemoteDatasetService<T, R extends APIRequest, D>
        extends AbstractRemoteDataService<Set<T>, R, D> {

    protected AbstractRemoteDatasetService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull JSONParsingStrategy<D> jsonParsingStrategy,
            @Nonnull DTOMapper<D, Set<T>> dtoMapper) {
        super(httpClient, jsonParser, jsonParsingStrategy, dtoMapper);
    }

    @Override
    protected final @Nonnull Set<T> fallbackData() {
        return Set.of();
    }
}
