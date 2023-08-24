// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAR_JAN_1_2000;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAR_JAN_2_2000;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExchangeRateRepositoryTests {

    @Autowired
    ExchangeRateRepository repository;

    @BeforeEach
    void resetRepo() {
        repository.deleteAll();
    }

    @Test
    void repoEmpty() {
        assertThat(repository.isEmpty()).isTrue();
    }

    @Test
    void repoNonEmpty() {
        repository.save(FOO_TO_BAR_JAN_1_2000);

        assertThat(repository.isEmpty()).isFalse();
    }

    @Test
    void findAllAsSetOnEmpty() {
        assertThat(repository.findAllAsSet()).isEmpty();
    }

    @Test
    void findAllAsSetOnNonEmpty() {
        repository.save(FOO_TO_BAR_JAN_1_2000);
        repository.save(FOO_TO_BAR_JAN_2_2000);

        Set<ExchangeRate> expected = Set.of(FOO_TO_BAR_JAN_1_2000, FOO_TO_BAR_JAN_2_2000);
        Set<ExchangeRate> actual = repository.findAllAsSet();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
