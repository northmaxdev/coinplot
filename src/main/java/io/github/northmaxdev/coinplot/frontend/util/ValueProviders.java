// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.util;

import com.vaadin.flow.function.ValueProvider;
import jakarta.annotation.Nonnull;

import java.util.Optional;
import java.util.function.Function;

public final class ValueProviders {

    private ValueProviders() {
        throw new UnsupportedOperationException();
    }

    public static <S, T> @Nonnull ValueProvider<S, T> of(@Nonnull Function<S, Optional<T>> f) {
        return s -> f.apply(s).orElse(null);
    }
}
