// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.db;

import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

// Repository interfaces that extend this one should generally override these
// inefficient default method implementations with more efficient JPQL queries.
@NoRepositoryBean
public interface EnhancedJPARepository<T, I> extends JpaRepository<T, I> {

    default boolean isEmpty() {
        return count() == 0L;
    }

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    default Set<T> findAllAsSet() {
        return new HashSet<>(findAll());
    }

    // CrudRepository::findById is not null-safe, according to its documentation
    default Optional<T> findByIDNullSafely(@Nullable I id) {
        return Optional.ofNullable(id)
                .flatMap(this::findById);
    }
}
