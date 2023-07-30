// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.db;

import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NoRepositoryBean
public interface ExtendedJPARepository<T, I> extends JpaRepository<T, I> {

    // Repository interfaces that extend this one should "override" these inefficient default implementations with more
    // efficient JPQL queries whenever possible.

    default boolean isEmpty() {
        return count() == 0L;
    }

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    default Set<T> findAllAsSet() {
        return new HashSet<>(findAll());
    }

    // findById is not null-safe
    default Optional<T> findByIDNullSafely(@Nullable I id) {
        return id == null ? Optional.empty() : findById(id);
    }
}
