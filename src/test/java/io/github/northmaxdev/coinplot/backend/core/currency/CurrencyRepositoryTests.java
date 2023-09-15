// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Disabled // FIXME
class CurrencyRepositoryTests {

    @Autowired
    CurrencyRepository repository;

    @BeforeEach
    void resetRepo() {
        repository.deleteAll();
    }
}
