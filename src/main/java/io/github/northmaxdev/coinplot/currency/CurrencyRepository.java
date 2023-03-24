// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    // Simple default impl to avoid writing enigmatic JPQL
    default boolean isEmpty() {
        return count() == 0L;
    }
}
