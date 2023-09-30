// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // TODO (Performance): Consider playing around with @DataJpaTest configurations
class ExchangeRateRepositoryTests {

    @Autowired
    ExchangeRateRepository repository;

    @BeforeEach
    void resetRepo() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() {
        // TODO (Refactor): This checks whether H2/Hibernate auto schema generation and Spring Data play nice with each other
    }
}
