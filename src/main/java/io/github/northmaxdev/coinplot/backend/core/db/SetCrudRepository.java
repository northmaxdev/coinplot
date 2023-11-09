// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.db;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Set;

// This class is the java.util.Set alternative for ListCrudRepository (see the latter's class JavaDoc for more information).
// If the entity type overrides equals/hashCode, and the implementations of those methods are based on the entity's primary
// key, then dealing with sets is semantically and functionally appropriate.
@NoRepositoryBean
public interface SetCrudRepository<T, I> extends CrudRepository<T, I> { // "Crud" instead of "CRUD" to match ListCrudRepository

    @Override
    <S extends T> @Nonnull Set<S> saveAll(@Nonnull Iterable<S> entities);

    @Override
    @Nonnull Set<T> findAll();

    @Override
    @Nonnull Set<T> findAllById(@Nonnull Iterable<I> ids);
}
