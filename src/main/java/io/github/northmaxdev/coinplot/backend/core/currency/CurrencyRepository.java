// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import io.github.northmaxdev.coinplot.backend.core.db.EnhancedJPARepository;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CurrencyRepository extends EnhancedJPARepository<Currency, String> {

    @Query("SELECT c FROM Currency c")
    @Nonnull Set<Currency> findAllAsSet();
}
