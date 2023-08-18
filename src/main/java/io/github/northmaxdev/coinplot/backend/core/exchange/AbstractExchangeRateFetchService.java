// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteResourceFetchService;
import io.github.northmaxdev.coinplot.backend.core.web.RemoteResourceFetchFailureException;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotFormAPIRequestException;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.http.HttpClient;
import java.util.Set;

public abstract class AbstractExchangeRateFetchService<R extends ExchangeRateSetRequest, D>
        extends AbstractRemoteResourceFetchService<R, D, Set<ExchangeRate>>
        implements ExchangeRateService {

    private final ExchangeRateRepository repository;

    protected AbstractExchangeRateFetchService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            ExchangeRateSetDTOMapper<D> dtoMapper,
            ExchangeRateRepository repository) {
        super(httpClient, jsonParser, dtoMapper);
        this.repository = repository;
    }

    @Override
    public final Set<ExchangeRate> getExchangeRates(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) throws FailedDataFetchException {
        try {
            R apiRequest = createAPIRequest(base, targets, dateInterval);

            // TODO: "Intelligent Caching" feature
            if (apiRequest.isExplicitlyDefined()) {
            } else {
            }

        } catch (CannotFormAPIRequestException e) {
            throw new RemoteResourceFetchFailureException(e);
        }
    }

    protected abstract @Nonnull R createAPIRequest(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) throws CannotFormAPIRequestException;
}
