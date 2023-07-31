// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static io.github.northmaxdev.coinplot.TestUtilities.BAR_FRANC;
import static io.github.northmaxdev.coinplot.TestUtilities.FOO_DOLLAR;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CurrencyRepositoryTests {

    @Autowired
    CurrencyRepository repository;

    @BeforeEach
    void resetRepo() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("isEmpty returns true on empty repository")
    void empty() {
        assertThat(repository.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("isEmpty returns false on non-empty repository")
    void notEmpty() {
        repository.save(FOO_DOLLAR);

        assertThat(repository.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("findAllAsSet returns empty Set on empty repository")
    void findAllAsSetOnEmpty() {
        assertThat(repository.findAllAsSet()).isEmpty();
    }

    @Test
    @DisplayName("findAllAsSet returns non-empty Set on non-empty repository")
    void findAllAsSetOnNonEmpty() {
        repository.save(FOO_DOLLAR);
        repository.save(BAR_FRANC);

        Set<Currency> expected = Set.of(FOO_DOLLAR, BAR_FRANC);
        Set<Currency> actual = repository.findAllAsSet();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
