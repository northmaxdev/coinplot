// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.db;

import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityExistenceQueryableRepository<T, I> extends CrudRepository<T, I> {

    // TODO: Give this method a better name, preferably something that follows Spring Data naming conventions
    default Percentage percentageOfExistingEntities(@Nonnull Iterable<I> ids) {
        return Percentage.ofMatchingPredicate(ids, this::existsByNullableId);
    }

    default boolean existsByNullableId(@Nullable I id) {
        return id != null && existsById(id);
    }
}
