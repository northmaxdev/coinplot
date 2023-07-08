// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import io.github.northmaxdev.coinplot.backend.db.ExtendedJPARepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CurrencyRepository extends ExtendedJPARepository<Currency, String> {

    @Query("SELECT c FROM Currency c")
    Set<Currency> findAllAsSet();
}
