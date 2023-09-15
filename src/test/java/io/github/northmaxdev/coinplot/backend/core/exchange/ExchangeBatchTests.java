// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.stream.Stream;

import static io.github.northmaxdev.coinplot.Tests.BAR_FRANC;
import static io.github.northmaxdev.coinplot.Tests.BAZ_POUND;
import static io.github.northmaxdev.coinplot.Tests.FOO_DOLLAR;
import static org.assertj.core.api.Assertions.assertThat;

class ExchangeBatchTests {

    @Test
    @DisplayName("stream produces expected contents")
    void stream() {
        ExchangeBatch batch = new ExchangeBatch(
                FOO_DOLLAR,
                Set.of(BAR_FRANC, BAZ_POUND),
                new LocalDateInterval(
                        LocalDate.of(2000, Month.JANUARY, 1),
                        LocalDate.of(2000, Month.JANUARY, 3)
                )
        );

        Stream<Exchange> expected = Stream.of(
                new Exchange(FOO_DOLLAR, BAR_FRANC, LocalDate.of(2000, Month.JANUARY, 1)),
                new Exchange(FOO_DOLLAR, BAZ_POUND, LocalDate.of(2000, Month.JANUARY, 1)),
                new Exchange(FOO_DOLLAR, BAR_FRANC, LocalDate.of(2000, Month.JANUARY, 2)),
                new Exchange(FOO_DOLLAR, BAZ_POUND, LocalDate.of(2000, Month.JANUARY, 2))
        );
        Stream<Exchange> actual = batch.stream();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected.toList()); // toList because this is a ListAssertion
    }
}
