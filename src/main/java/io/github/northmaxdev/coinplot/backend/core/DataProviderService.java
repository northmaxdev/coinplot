// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.fixer.Fixer;
import io.github.northmaxdev.coinplot.backend.frankfurter.Frankfurter;
import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public final class DataProviderService {

    @Value("${provider}")
    private @Nullable String selectedProviderID = null;
    private final @Nonnull Map<String, DataProvider> availableProviders;
    private final @Nonnull DataProvider defaultProvider;

    @Autowired
    public DataProviderService(@Nonnull Fixer fixer, @Nonnull Frankfurter frankfurter) {
        availableProviders = Map.of(
                fixer.getID(), fixer,
                frankfurter.getID(), frankfurter
        );
        defaultProvider = frankfurter; // Subject to change
    }

    public Optional<DataProvider> getSelectedProvider() {
        return Strings.blankToOptional(selectedProviderID)
                .map(availableProviders::get);
    }

    public @Nonnull DataProvider getSelectedProviderOrDefault() {
        return getSelectedProvider()
                .orElse(defaultProvider);
    }

    public @Nonnull Collection<DataProvider> getAvailableProviders() {
        return Collections.unmodifiableCollection(availableProviders.values());
    }
}
