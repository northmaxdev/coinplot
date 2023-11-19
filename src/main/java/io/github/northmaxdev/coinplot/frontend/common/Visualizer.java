// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;

public interface Visualizer<T> {

    void visualize(@Nonnull T obj);

    void clear();

    default void visualizeOrClear(@Nullable T obj) {
        if (obj == null) {
            clear();
        } else {
            visualize(obj);
        }
    }

    default void visualizeOrClear(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<T> optional) {
        optional.ifPresentOrElse(this::visualize, this::clear);
    }
}
