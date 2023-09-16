// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.db.EntityExistenceQueryableRepository;
import io.github.northmaxdev.coinplot.backend.core.db.SetCrudRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;

@Repository
public interface ExchangeRateRepository extends
        SetCrudRepository<ExchangeRate, Exchange>,
        EntityExistenceQueryableRepository<ExchangeRate, Exchange> {

    // TODO:
    //  Consider overriding existAllById(Iterable<Exchange>) with a JPQL query,
    //  as it is a frequently-accessed method.

    default boolean existAllById(@Nonnull ExchangeBatch batch) {
        Objects.requireNonNull(batch);
        @Nullable Boolean result = existAllById(batch.toSet());
        if (result == null) {
            // Since an ExchangeBatch cannot be empty, this should never happen
            throw new IllegalStateException("Bug: existAllById(ExchangeBatch.toSet()) returned null");
        }
        return result;
    }

    // TODO:
    //  Consider overriding findAllById(Iterable<Exchange>) with a JPQL query,
    //  as it is a frequently-accessed method.

    default @Nonnull Set<ExchangeRate> findAllById(@Nonnull ExchangeBatch batch) {
        Objects.requireNonNull(batch);
        return findAllById(batch.toSet());
    }
}
