// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import jakarta.annotation.Nonnull;

import java.util.Optional;

public interface Visualizer<T> {

    void visualize(@Nonnull T obj);

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    default void visualize(Optional<T> optional) {
        optional.ifPresentOrElse(this::visualize, this::clear);
    }

    void clear();
}
