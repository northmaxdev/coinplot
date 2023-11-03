// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public record GeneralPurposePair<F, S>(@Nonnull F first, @Nonnull S second) implements Pair<F, S> {

    public GeneralPurposePair(@Nonnull F first, @Nonnull S second) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
    }

    @Override
    public @Nonnull F getFirst() {
        return first;
    }

    @Override
    public @Nonnull S getSecond() {
        return second;
    }
}
