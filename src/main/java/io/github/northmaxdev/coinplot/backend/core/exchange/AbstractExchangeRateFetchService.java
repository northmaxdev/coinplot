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
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public final @Nonnull Set<ExchangeRate> getExchangeRates(
            @Nonnull Currency base,
            @Nonnull Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) throws FailedDataFetchException {
        try {
            // The general idea:
            // [1] Fetch from the repository first. There are three options for what we could get:
            //     [1.1] The returned set contains ALL the stuff we requested
            //     [1.2] The returned set contains a part of the stuff we requested
            //     [1.3] The returned set contains none of the stuff we requested
            // [2] Create an ExchangeRateSetRequest from our query, then immediately reduce it by
            //     the stuff we fetched from repo:
            //     [2.1] Case [1.1] --> the created request gets reduced to nothing, which means we
            //           have all the required data in the repo, therefore, return without sending
            //           any API requests.
            //     [2.2] Case [1.2] --> the created request only contains the stuff we are missing,
            //           therefore, send the API request, fill the repo and re-fetch again
            //     [2.3] Case [1.3] --> the created request contains the entire query, therefore,
            //           send the API request, fill the repo and re-fetch again
            throw new UnsupportedOperationException();
        } catch (CannotCreateAPIRequestException e) {
            throw new FailedRemoteDataFetchException(e);
        }
    }

    protected abstract @Nonnull R createAPIRequest(
            @Nonnull Currency base,
            @Nonnull Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) throws CannotCreateAPIRequestException;
}
