// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteDataFetchService;
import io.github.northmaxdev.coinplot.backend.core.web.FailedRemoteDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.http.HttpClient;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCurrencyFetchService<R extends CurrencySetRequest, D>
        extends AbstractRemoteDataFetchService<R, D, Set<Currency>>
        implements CurrencyService {

    private final @Nonnull CurrencyRepository repository;

    protected AbstractCurrencyFetchService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull CurrencySetDTOMapper<D> dtoMapper,
            @Nonnull CurrencyRepository repository) {
        super(httpClient, jsonParser, dtoMapper);
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public final @Nonnull Set<Currency> getAvailableCurrencies() throws FailedDataFetchException {
        fetchIntoRepoIfEmpty();
        return repository.findAll();
    }

    @Override
    public final Optional<Currency> getCurrency(@Nullable String code) throws FailedDataFetchException {
        fetchIntoRepoIfEmpty();

        // TODO: This could be a general-purpose repository method
        return Optional.ofNullable(code)
                .flatMap(repository::findById);
    }

    protected abstract @Nonnull R createAPIRequest() throws CannotCreateAPIRequestException;

    private void fetchIntoRepoIfEmpty() throws FailedRemoteDataFetchException {
        if (repository.isNotEmpty()) {
            return;
        }

        try {
            R apiRequest = createAPIRequest();
            Set<Currency> currencies = fetch(apiRequest);
            repository.saveAll(currencies);
        } catch (CannotCreateAPIRequestException e) {
            throw new FailedRemoteDataFetchException(e);
        }
    }
}
