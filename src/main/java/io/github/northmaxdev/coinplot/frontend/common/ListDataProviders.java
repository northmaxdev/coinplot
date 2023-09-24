// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.data.provider.ListDataProvider;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class ListDataProviders {

    private ListDataProviders() {
        throw new UnsupportedOperationException();
    }

    @SafeVarargs
    public static <T> @Nonnull ListDataProvider<T> create(@Nonnull T... items) {
        Collection<T> backingCollection = List.of(items); // Implicit null-checks on both the array itself and its elements
        return new ListDataProvider<>(backingCollection);
    }

    @SafeVarargs
    public static <T> @Nonnull ListDataProvider<T> create(@Nonnull Comparator<T> sortComparator, @Nonnull T... items) {
        Objects.requireNonNull(sortComparator);
        ListDataProvider<T> dataProvider = create(items);
        dataProvider.setSortComparator(sortComparator::compare); // See SerializableComparator JavaDoc for potentially important info
        return dataProvider;
    }

    public static <T> @Nonnull ListDataProvider<T> create(@Nonnull Collection<T> items) {
        Collection<T> backingCollection = List.copyOf(items); // Implicit null-checks on both the array itself and its elements
        return new ListDataProvider<>(backingCollection);
    }

    public static <T> @Nonnull ListDataProvider<T> create(@Nonnull Comparator<T> sortComparator, @Nonnull Collection<T> items) {
        Objects.requireNonNull(sortComparator);
        ListDataProvider<T> dataProvider = create(items);
        dataProvider.setSortComparator(sortComparator::compare); // See SerializableComparator JavaDoc for potentially important info
        return dataProvider;
    }
}
