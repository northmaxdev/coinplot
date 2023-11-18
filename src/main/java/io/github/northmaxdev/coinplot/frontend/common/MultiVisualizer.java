// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public interface MultiVisualizer<T> extends Visualizer<T> {

    void visualize(@Nonnull Collection<T> objects);

    @Override
    default void visualize(@Nonnull T obj) {
        Objects.requireNonNull(obj);

        Collection<T> collection = Collections.singleton(obj);
        visualize(collection);
    }
}
