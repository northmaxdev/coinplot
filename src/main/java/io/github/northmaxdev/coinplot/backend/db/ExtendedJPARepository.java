// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.HashSet;
import java.util.Set;

@NoRepositoryBean
public interface ExtendedJPARepository<T, I> extends JpaRepository<T, I> {

    // TODO: JPQL
    default boolean isEmpty() {
        return count() == 0L;
    }

    // TODO: JPQL
    default Set<T> findAllAsSet() {
        return new HashSet<>(findAll());
    }
}
