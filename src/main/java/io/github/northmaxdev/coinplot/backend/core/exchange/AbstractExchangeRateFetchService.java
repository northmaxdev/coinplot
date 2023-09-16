// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteDataFetchService;
import io.github.northmaxdev.coinplot.backend.core.web.FailedRemoteDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
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
    public final @Nonnull Set<ExchangeRate> getExchangeRates(@Nonnull ExchangeBatch exchanges) throws FailedDataFetchException {
        // Cases:
        //     The desired data is FULLY available in the repo -> no requests sent, load from repo directly
        //     The desired data is NOT available in the repo -> send a request, populate repo
        //     The desired data is PARTIALLY available in the repo -> send a request as if the data is not available at all, populate repo
        // Needless to say, asking for data that is either fully present or fully absent is handled efficiently,
        // while asking for partially present data should lead to operational redundancies (and thus, performance hits).

        // 'exchanges' is implicitly null-checked
        if (repository.existAllById(exchanges)) {
            return repository.findAllById(exchanges);
        }

        try {
            R request = createAPIRequest(exchanges);
            Set<ExchangeRate> exchangeRates = fetch(request);
            return repository.saveAll(exchangeRates);
        } catch (CannotCreateAPIRequestException e) {
            throw new FailedRemoteDataFetchException(e);
        }
    }

    protected abstract @Nonnull R createAPIRequest(@Nonnull ExchangeBatch exchanges) throws CannotCreateAPIRequestException;
}
