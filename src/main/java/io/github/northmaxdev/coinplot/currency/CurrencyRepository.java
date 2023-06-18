// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
