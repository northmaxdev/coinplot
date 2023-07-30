// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.ResourceFetchFailureException;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractRemoteResourceFetchService;
import io.github.northmaxdev.coinplot.backend.core.web.RemoteResourceFetchFailureException;
import io.github.northmaxdev.coinplot.backend.core.web.request.InsufficientInfoForRequestException;
import jakarta.annotation.Nullable;

import java.net.http.HttpClient;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCurrencyFetchService<R extends CurrencySetRequest, D>
        extends AbstractRemoteResourceFetchService<R, D, Set<Currency>>
        implements CurrencyService {

    private final CurrencyRepository repository;

    protected AbstractCurrencyFetchService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencySetDTOMapper<D> dtoMapper,
            CurrencyRepository repository) {
        super(httpClient, jsonParser, dtoMapper);
        this.repository = repository;
    }

    @Override
    public final Set<Currency> getAvailableCurrencies() throws ResourceFetchFailureException {
        fetchIntoRepoIfEmpty();
        return repository.findAllAsSet();
    }

    @Override
    public final Optional<Currency> getCurrency(@Nullable String code) throws ResourceFetchFailureException {
        fetchIntoRepoIfEmpty();
        return repository.findByIDNullSafely(code);
    }

    protected abstract R createAPIRequest() throws InsufficientInfoForRequestException;

    private void fetchIntoRepoIfEmpty() throws RemoteResourceFetchFailureException {
        if (repository.isNotEmpty()) {
            return;
        }

        try {
            R apiRequest = createAPIRequest();
            Set<Currency> currencies = fetch(apiRequest);
            repository.saveAll(currencies);
        } catch (InsufficientInfoForRequestException e) {
            throw new RemoteResourceFetchFailureException(e);
        }
    }
}
