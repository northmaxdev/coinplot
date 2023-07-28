// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.db.ExtendedJPARepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends ExtendedJPARepository<ExchangeRate, Exchange> {}
