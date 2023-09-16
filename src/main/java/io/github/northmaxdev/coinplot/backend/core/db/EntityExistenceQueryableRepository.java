// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.db;

import io.github.northmaxdev.coinplot.lang.Iterables;
import jakarta.annotation.Nullable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityExistenceQueryableRepository<T, I> extends CrudRepository<T, I> {

    default boolean existsByNullableId(@Nullable I id) {
        return id != null && existsById(id);
    }

    // A conceptual merge of the following methods:
    //     boolean existsById(I);
    //     Iterable<T> findAllById(Iterable<I>);
    // Cases:
    //     true -> If 'ids' is non-empty and ALL the elements exist within this repository
    //     false -> If 'ids' is non-empty and NOT ALL the elements exist within this repository, if any
    //     null -> If 'ids' is null or empty
    default @Nullable Boolean existAllById(@Nullable Iterable<I> ids) {
        return Iterables.isNullOrEmpty(ids) ? null : Iterables.stream(ids)
                .allMatch(this::existsByNullableId);
    }
}
