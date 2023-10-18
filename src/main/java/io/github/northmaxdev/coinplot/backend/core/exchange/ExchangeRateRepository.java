// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.db.EntityExistenceQueryableRepository;
import io.github.northmaxdev.coinplot.backend.core.db.SetCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends
        SetCrudRepository<ExchangeRate, Exchange>,
        EntityExistenceQueryableRepository<ExchangeRate, Exchange> {}
