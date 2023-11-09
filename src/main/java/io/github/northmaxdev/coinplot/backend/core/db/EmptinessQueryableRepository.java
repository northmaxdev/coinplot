// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EmptinessQueryableRepository<T, I> extends CrudRepository<T, I> {

    default boolean isEmpty() {
        return count() == 0L;
    }

    default boolean isNotEmpty() {
        return !isEmpty();
    }
}
