// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CurrencyRepositoryTests {

    static final Currency FOO = new Currency("FOO", "Foo Dollar");

    @Autowired
    CurrencyRepository repository;

    @BeforeEach
    void resetRepo() {
        repository.deleteAll();
    }

    @Test
    void empty() {
        assertThat(repository.isEmpty()).isTrue();
    }

    @Test
    void notEmpty() {
        repository.save(FOO);

        assertThat(repository.isEmpty()).isFalse();
    }

    @Test
    void findAllAsSetOnEmpty() {
        assertThat(repository.findAllAsSet()).isEmpty();
    }

    @Test
    void findAllAsSetOnNonEmpty() {
        repository.save(FOO);

        assertThat(repository.findAllAsSet()).isEqualTo(Set.of(FOO));
    }
}
