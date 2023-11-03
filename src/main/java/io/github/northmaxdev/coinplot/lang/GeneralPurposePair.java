package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

record GeneralPurposePair<F, S>(@Nonnull F first, @Nonnull S second) implements Pair<F, S> {

    @Override
    public @Nonnull F getFirst() {
        return first;
    }

    @Override
    public @Nonnull S getSecond() {
        return second;
    }
}
