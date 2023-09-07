// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.db.EnhancedJPARepository;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExchangeRateRepository extends EnhancedJPARepository<ExchangeRate, Exchange> {

    // TODO:
    //  0. Unit tests
    //  1. Implement as Java code
    //  2. Profile
    //  3. Implement as a JPQL query if needed
    @Nonnull Set<ExchangeRate> findMatching(
            @Nonnull Currency base,
            @Nonnull Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval);
}
