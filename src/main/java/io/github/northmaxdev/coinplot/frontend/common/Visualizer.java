// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import jakarta.annotation.Nonnull;

public interface Visualizer<T> {

    void visualize(@Nonnull T obj);

    void clear();
}
