// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface Pair<F, S> {

    @Nonnull F getFirst();

    @Nonnull S getSecond();

    static <F, S> @Nonnull Pair<F, S> of(@Nonnull F first, @Nonnull S second) {
        return new GeneralPurposePair<>(first, second);
    }

    default boolean matches(@Nonnull BiPredicate<F, S> predicate) {
        return predicate.test(getFirst(), getSecond());
    }

    default boolean firstMatches(@Nonnull Predicate<F> predicate) {
        return predicate.test(getFirst());
    }

    default boolean secondMatches(@Nonnull Predicate<S> predicate) {
        return predicate.test(getSecond());
    }
}
