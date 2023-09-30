// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import io.github.northmaxdev.coinplot.backend.core.db.EmptinessQueryableRepository;
import io.github.northmaxdev.coinplot.backend.core.db.SetCrudRepository;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CurrencyRepository extends
        SetCrudRepository<Currency, String>,
        EmptinessQueryableRepository<Currency, String> {

    @Override // This is for performance only
    @Query("SELECT c FROM Currency c")
    @Nonnull Set<Currency> findAll();
}
