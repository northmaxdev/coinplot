// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public interface MultiVisualizer<T> extends Visualizer<T> {

    // Generally speaking, most implementors will override this with something more efficient.
    @Override
    default void visualize(@Nonnull T obj) {
        Objects.requireNonNull(obj);

        Collection<T> collection = Collections.singleton(obj);
        visualize(collection);
    }

    void visualize(@Nonnull Collection<T> objects);
}
