// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteDatasetService;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONParsingStrategy;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.http.HttpClient;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCurrencyFetchService<R extends CurrencySetRequest, D>
        extends AbstractRemoteDatasetService<Currency, R, D>
        implements CurrencyService {

    private final @Nonnull CurrencyRepository repository;

    protected AbstractCurrencyFetchService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull JSONParsingStrategy<D> jsonParsingStrategy,
            @Nonnull CurrencySetDTOMapper<D> dtoMapper,
            @Nonnull CurrencyRepository repository) {
        super(httpClient, jsonParser, jsonParsingStrategy, dtoMapper);
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public final @Nonnull Set<Currency> getAvailableCurrencies() {
        if (repository.isEmpty()) {
            R request = createAPIRequest();
            Set<Currency> currencies = fetch(request);
            return repository.saveAll(currencies);
        }
        return repository.findAll();
    }

    @Override
    public final Optional<Currency> getCurrency(@Nullable String code) {
        if (code == null) { // No need to query the repo at all in this case
            return Optional.empty();
        }

        if (repository.isEmpty()) {
            R request = createAPIRequest();
            Set<Currency> currencies = fetch(request);
            repository.saveAll(currencies);
        }
        return repository.findById(code);
    }

    protected abstract @Nonnull R createAPIRequest();
}
