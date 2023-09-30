// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ExchangeRateRepositoryTests { // FIXME (Implementation)

    @Autowired
    ExchangeRateRepository repository;

    @BeforeEach
    void resetRepo() {
        repository.deleteAll();
    }
}
