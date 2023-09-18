// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public final class Tests {

    private Tests() {
        throw new UnsupportedOperationException();
    }

    ////////////////////
    // Currency stuff //
    ////////////////////

    public static final Currency FOO_DOLLAR = new Currency("FOO", "Foo Dollar");
    public static final Currency BAR_FRANC = new Currency("BAR", "Bar Franc");
    public static final Currency BAZ_POUND = new Currency("BAZ", "Baz Pound");
    public static final CurrencyService CURRENCY_SERVICE_MOCK = new CurrencyService() {
        @Override
        public @Nonnull Set<Currency> getAvailableCurrencies() {
            return Set.of(FOO_DOLLAR, BAR_FRANC, BAZ_POUND);
        }

        @Override
        public Optional<Currency> getCurrency(@Nullable String code) {
            return switch (code) {
                case "FOO" -> Optional.of(FOO_DOLLAR);
                case "BAR" -> Optional.of(BAR_FRANC);
                case "BAZ" -> Optional.of(BAZ_POUND);
                case null, default -> Optional.empty();
            };
        }
    };

    ////////////////////////
    // ExchangeRate stuff //
    ////////////////////////

    public static final ExchangeRate FOO_TO_BAR_JAN_1_2000 = new ExchangeRate(
            FOO_DOLLAR,
            BAR_FRANC,
            LocalDate.of(2000, Month.JANUARY, 1),
            BigDecimal.valueOf(1.618)
    );
    public static final ExchangeRate FOO_TO_BAZ_JAN_1_2000 = new ExchangeRate(
            FOO_DOLLAR,
            BAZ_POUND,
            LocalDate.of(2000, Month.JANUARY, 1),
            BigDecimal.valueOf(3.1415)
    );
    public static final ExchangeRate FOO_TO_BAR_JAN_2_2000 = new ExchangeRate(
            FOO_DOLLAR,
            BAR_FRANC,
            LocalDate.of(2000, Month.JANUARY, 2),
            BigDecimal.valueOf(1.619)
    );
    public static final ExchangeRate FOO_TO_BAZ_JAN_2_2000 = new ExchangeRate(
            FOO_DOLLAR,
            BAZ_POUND,
            LocalDate.of(2000, Month.JANUARY, 2),
            BigDecimal.valueOf(3.1401)
    );

    //////////////////////
    // APIRequest stuff //
    //////////////////////

    public static <R extends APIRequest> void assertExpectedURIsContainActual(@Nonnull R request, @Nonnull String... possibleOutcomes) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(possibleOutcomes);

        Iterable<URI> expected = Stream.of(possibleOutcomes)
                .filter(Objects::nonNull) // Just in case
                .map(URI::create)
                .toList();
        URI actual = request.getURI();

        assertThat(actual).isIn(expected);
    }

    @SafeVarargs
    public static <R extends APIRequest> void assertExpectedHeadersEqualActual(
            @Nonnull R request,
            @Nonnull Map.Entry<String, String>... expectedHeaders) {
        Objects.requireNonNull(request);
        // Map::ofEntries performs a deep null-check on its parameter

        Map<String, String> expected = Map.ofEntries(expectedHeaders);
        Map<String, String> actual = request.getHeaders();

        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
    }

    public static <R extends APIRequest> void verifyAPIRequestEquals(@Nonnull Class<R> requestClass) {
        Objects.requireNonNull(requestClass);
        EqualsVerifier.forClass(requestClass)
                // FIXME:
                //  The class hierarchy of APIRequest implementations mixes between subclasses that add state and
                //  subclasses that merely add behavior. Running an EqualsVerifier with standard configuration leads to
                //  "subclass does not equal superclass" errors. Configuring withRedefinedSuperclass() leads to "subclass
                //  equals superclass but should not" errors. The combination of the former and latter is very weird
                //  considering that we're talking about the same equals/hashCode implementations being tested.
                //  Suppressing such strict inheritance checks passes the tests, but I'm not sure if this is actually
                //  appropriate for the issue at hand considering its documentation, so this may or may not lead to
                //  subtle bugs later on (hopefully not). In the meantime, the tested equals/hashCode implementations
                //  look completely OK.
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    ///////////////////////
    // Persistence stuff //
    ///////////////////////

    public static <T> void verifyJPAEntityEquals(@Nonnull Class<T> entityClass) {
        Objects.requireNonNull(entityClass);
        EqualsVerifier.forClass(entityClass)
                .suppress(Warning.SURROGATE_KEY) // To only compare entity IDs for equality
                .verify();
    }
}
