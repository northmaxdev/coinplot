// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.db;

import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityExistenceQueryableRepository<T, I> extends CrudRepository<T, I> {

    default Percentage calculateExistencePercentage(@Nonnull Iterable<I> ids) {
        return Percentage.ofMatchingPredicate(ids, this::existsByNullableId);
    }

    default boolean existsByNullableId(@Nullable I id) {
        return id != null && existsById(id);
    }
}
