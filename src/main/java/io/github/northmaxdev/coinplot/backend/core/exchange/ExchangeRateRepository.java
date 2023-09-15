// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.db.SetCrudRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;

@Repository
public interface ExchangeRateRepository extends SetCrudRepository<ExchangeRate, Exchange> {

    // TODO:
    //  Consider overriding findAllById(Iterable<Exchange>) with a JPQL query,
    //  as it is a frequently-accessed method.

    default @Nonnull Set<ExchangeRate> findAllById(@Nonnull ExchangeBatch batch) {
        Objects.requireNonNull(batch);
        return findAllById(batch.toSet());
    }
}
