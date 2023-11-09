// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record Pair<F, S>(@Nonnull F first, @Nonnull S second) {

    public Pair(@Nonnull F first, @Nonnull S second) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
    }

    public boolean matches(@Nonnull BiPredicate<F, S> predicate) {
        return predicate.test(first, second);
    }

    public boolean firstMatches(@Nonnull Predicate<F> predicate) {
        return predicate.test(first);
    }

    public boolean secondMatches(@Nonnull Predicate<S> predicate) {
        return predicate.test(second);
    }
}
