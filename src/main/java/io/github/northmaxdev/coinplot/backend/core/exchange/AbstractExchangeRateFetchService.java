// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteDatasetService;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONParsingStrategy;
import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;

import java.net.http.HttpClient;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractExchangeRateFetchService<R extends ExchangeRateSetRequest, D>
        extends AbstractRemoteDatasetService<ExchangeRate, R, D>
        implements ExchangeRateService {

    // This is the threshold value that decides if we use cached data.
    // Given a set of exchanges, we check how many of them exist in the repo,
    // and if the % exceeds the threshold, we load from cache instead of API calls.
    // Increasing the threshold value will result in fewer cache hits and denser data overall.
    // Decreasing the threshold value will result in more cache hits and sparser data overall.
    private static final Percentage CACHE_LOAD_THRESHOLD = new Percentage(70L);

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
    public final @Nonnull Set<ExchangeRate> getAvailableExchangeRates(@Nonnull ExchangeBatch exchangeBatch) {
        Objects.requireNonNull(exchangeBatch);
        Logger logger = getLogger();

        Set<Exchange> exchanges = exchangeBatch.toSet();
        Percentage cachedExchangeRatesPercentage = repository.calculateExistencePercentage(exchanges);
        logger.info("{} of requested exchange rates are present in cache", cachedExchangeRatesPercentage);

        if (cachedExchangeRatesPercentage.compareTo(CACHE_LOAD_THRESHOLD) > 0) {
            Set<ExchangeRate> cachedExchangeRates = repository.findAllById(exchanges);
            logger.info("Loaded {} exchange rates from cache (no API requests were sent)", cachedExchangeRates.size());
            return Collections.unmodifiableSet(cachedExchangeRates);
        } else {
            R request = createAPIRequest(exchangeBatch);
            Set<ExchangeRate> fetchedExchangeRates = fetch(request);
            Set<ExchangeRate> savedExchangeRates = repository.saveAll(fetchedExchangeRates);
            return Collections.unmodifiableSet(savedExchangeRates);
        }
    }

    // No need for implementors to null-check 'exchangeBatch'
    protected abstract @Nonnull R createAPIRequest(@Nonnull ExchangeBatch exchangeBatch);
}
