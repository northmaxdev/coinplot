// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.db.EnhancedJPARepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends EnhancedJPARepository<ExchangeRate, Exchange> {

    // TODO:
    //  JPQL implementations of at least some of EnhancedJPARepository
    //  default methods (for performance reasons)
}
