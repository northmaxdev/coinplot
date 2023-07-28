// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.ResourceFetchException;
import io.github.northmaxdev.coinplot.backend.core.web.AbstractHTTPService;
import jakarta.annotation.Nullable;

import java.net.http.HttpClient;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCurrencyService<R extends CurrencySetRequest, D>
        extends AbstractHTTPService<R, D, Set<Currency>>
        implements CurrencyService {

    private final CurrencyRepository repository;

    protected AbstractCurrencyService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencySetDTOMapper<D> dtoMapper,
            CurrencyRepository repository) {
        super(httpClient, jsonParser, dtoMapper);
        this.repository = repository;
    }

    @Override
    public final Set<Currency> getAvailableCurrencies() throws ResourceFetchException {
        fetchIfEmptyRepository();
        return repository.findAllAsSet();
    }

    @Override
    public final Optional<Currency> getCurrency(@Nullable String code) throws ResourceFetchException {
        fetchIfEmptyRepository();
        return repository.findByIDNullSafely(code);
    }

    protected abstract Optional<R> createAPIRequest();

    private void fetchIfEmptyRepository() throws ResourceFetchException {
        if (repository.isEmpty()) {
            Optional<R> apiRequest = createAPIRequest();
            Set<Currency> fetchedCurrencies = fetch(apiRequest);
            repository.saveAll(fetchedCurrencies);
        }
    }
}
