// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Service
public final class DataProviderService {

    private final @Nonnull DataProviderConfiguration config;
    private final @Nonnull Map<String, DataProvider> availableProviders;

    @Autowired
    public DataProviderService(@Nonnull DataProviderConfiguration config, @Nonnull DataProvider... dataProviders) {
        this.config = Objects.requireNonNull(config);
        availableProviders = Stream.of(dataProviders)
                .collect(toMap(DataProvider::getID, p -> p));
    }

    public @Nonnull DataProvider getSelectedProviderOrDefault() {
        return config.safelyGetSelectedProviderID()
                .map(availableProviders::get)
                .orElse(DataProvider.NO_OP);
    }

    public @Nonnull Collection<DataProvider> getAvailableProviders() {
        return Collections.unmodifiableCollection(availableProviders.values());
    }
}
