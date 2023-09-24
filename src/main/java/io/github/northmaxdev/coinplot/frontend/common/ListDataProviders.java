// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.data.provider.ListDataProvider;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public final class ListDataProviders {

    private ListDataProviders() {
        throw new UnsupportedOperationException();
    }

    // The methods below use java.util.Set as the backing collection because in most cases,
    // the related component wants its items to be unique. Needless to say, type T must have
    // well-defined equals/hashCode implementations.

    @SafeVarargs
    public static <T> @Nonnull ListDataProvider<T> create(@Nonnull T... items) {
        Collection<T> backingCollection = Set.of(items); // Implicit null-checks on both the array itself and its elements
        return new ListDataProvider<>(backingCollection);
    }

    @SafeVarargs
    public static <T> @Nonnull ListDataProvider<T> create(@Nonnull Comparator<T> sortComparator, @Nonnull T... items) {
        Objects.requireNonNull(sortComparator);
        ListDataProvider<T> dataProvider = create(items);
        dataProvider.setSortComparator(sortComparator::compare); // See SerializableComparator JavaDoc for potentially important info
        return dataProvider;
    }
}
