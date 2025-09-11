// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.util.Currency;
import java.util.Objects;
import java.util.function.Function;


/**
 * This class exists for when we need to map just the exchange itself (no date) to something.
 * <p>Example:</p>
 * <pre>{@code
 * Map<CurrencyExchange, BigDecimal>
 *
 * {
 *     [USD, CHF, 2019-12-31] : 0.96617
 *     [USD, EUR, 2019-12-31] : 0.89015
 *     [USD, CHF, 2020-12-31] : 0.88029
 *     [USD, EUR, 2020-12-31] : 0.81493
 * }}
 * </pre>
 * <p>
 * to:
 * <p>
 * <pre>{@code
 * Map<DatelessCurrencyExchange, Collection<T>>
 *
 * {
 *     [USD, CHF] : (T, T, ...)
 *     [USD, EUR] : (T, T, ...)
 * }}
 * </pre>
 * <p>where the date and exchange rate value get mapped to a bunch of {@code T}'s.</p>
 * <p>Needless to say, {@code equals}/{@code hashCode} correctness are critical here.</p>
 *
 * @see java.util.stream.Collectors#groupingBy(Function)
 */
public record DatelessCurrencyExchange(Currency base, Currency target) {

    public DatelessCurrencyExchange {
        Objects.requireNonNull(base, "base must not be null");
        Objects.requireNonNull(target, "target must not be null");
    }

    @Override
    public String toString() {
        return base.getCurrencyCode() + "-->" + target.getCurrencyCode();
    }
}
