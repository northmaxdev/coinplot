// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteDatasetService;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONParsingStrategy;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;

import java.net.http.HttpClient;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractExchangeRateFetchService<R extends ExchangeRateSetRequest, D>
        extends AbstractRemoteDatasetService<ExchangeRate, R, D>
        implements ExchangeRateService {

    private final @Nonnull ExchangeRateRepository repository;

    protected AbstractExchangeRateFetchService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull JSONParsingStrategy<D> jsonParsingStrategy,
            @Nonnull ExchangeRateSetDTOMapper<D> dtoMapper,
            @Nonnull ExchangeRateRepository repository) {
        super(httpClient, jsonParser, jsonParsingStrategy, dtoMapper);
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public final @Nonnull Set<ExchangeRate> getAvailableExchangeRates(@Nonnull ExchangeBatch exchanges) {
        // Cases:
        //     The desired data is FULLY available in the repo -> no requests sent, load from repo directly
        //     The desired data is NOT available in the repo -> send a request, populate repo
        //     The desired data is PARTIALLY available in the repo -> send a request as if the data is not available at all, populate repo
        // Needless to say, asking for data that is either fully present or fully absent is handled efficiently,
        // while asking for partially present data should lead to operational redundancies (and thus, potential performance hits).
        Objects.requireNonNull(exchanges);
        Logger logger = getLogger();

        if (repository.existAllById(exchanges)) {
            Set<ExchangeRate> cachedExchangeRates = repository.findAllById(exchanges);
            logger.info("Loaded {} exchange rates from cache (no API requests were sent)", cachedExchangeRates.size());
            return cachedExchangeRates;
        }

        // Use fetch(APIRequestFactory) as it handles CannotCreateAPIRequestException for us
        Set<ExchangeRate> fetchedExchangeRates = fetch(() -> createAPIRequest(exchanges));
        return repository.saveAll(fetchedExchangeRates);
    }

    // No need for implementors to null-check 'exchanges'
    protected abstract @Nonnull R createAPIRequest(@Nonnull ExchangeBatch exchanges) throws CannotCreateAPIRequestException;
}
