// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteDataFetchService;
import io.github.northmaxdev.coinplot.backend.core.web.FailedRemoteDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.http.HttpClient;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractExchangeRateFetchService<R extends ExchangeRateSetRequest, D>
        extends AbstractRemoteDataFetchService<R, D, Set<ExchangeRate>>
        implements ExchangeRateService {

    private final @Nonnull ExchangeRateRepository repository;

    protected AbstractExchangeRateFetchService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull ExchangeRateSetDTOMapper<D> dtoMapper,
            @Nonnull ExchangeRateRepository repository) {
        super(httpClient, jsonParser, dtoMapper);
        this.repository = Objects.requireNonNull(repository, "repository is null");
    }

    @Override
    public final Set<ExchangeRate> getExchangeRates(@Nullable Currency base,
                                                    Set<Currency> targets,
                                                    @Nonnull LocalDateInterval dateInterval) throws FailedDataFetchException {
        try {
            R apiRequest = createAPIRequest(base, targets, dateInterval);

            // TODO: "Intelligent Caching" feature
            if (apiRequest.isExplicitlyDefined()) {
            } else {
            }

            // This is here temporarily so the codebase is able to compile
            throw new UnsupportedOperationException();

        } catch (CannotCreateAPIRequestException e) {
            throw new FailedRemoteDataFetchException(e);
        }
    }

    protected abstract @Nonnull R createAPIRequest(@Nullable Currency base,
                                                   Set<Currency> targets,
                                                   @Nonnull LocalDateInterval dateInterval) throws CannotCreateAPIRequestException;
}
