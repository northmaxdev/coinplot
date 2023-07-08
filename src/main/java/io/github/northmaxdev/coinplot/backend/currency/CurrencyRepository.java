// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import io.github.northmaxdev.coinplot.backend.db.ExtendedJPARepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends ExtendedJPARepository<Currency, String> {
}
