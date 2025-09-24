// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Currency;

import static java.util.Objects.requireNonNull;

/**
 * This class closely adheres to established forex terminology, notations and conventions.
 *
 * @param base  the base currency (the one you'd be selling)
 * @param quote the quote currency (the one you'd be buying)
 * @see <a href="https://en.wikipedia.org/wiki/Currency_pair">Currency pair - Wikipedia</a>
 * @see <a href="https://corporatefinanceinstitute.com/resources/foreign-exchange/currency-pair/">Currency Pair - CFI</a>
 * @see <a href="https://www.investopedia.com/terms/c/currencypair.asp">Understanding Currency Pairs: Major, Minor, and Exotic Examples - Investopedia</a>
 */
public record CurrencyPair(Currency base, Currency quote) {

    private static final BigDecimal DEFAULT_PIP_DEFINITION = new BigDecimal("0.0001");
    private static final BigDecimal JAPANESE_YEN_PIP_DEFINITION = new BigDecimal("0.01");
    private static final Currency JAPANESE_YEN = Currency.getInstance("JPY");

    public CurrencyPair {
        requireNonNull(base);
        requireNonNull(quote);
    }

    /**
     * Shortcut to create a {@link CurrencyPair} from ISO 4217 currency codes directly.
     *
     * @apiNote Validation of the currency codes is immediately delegated to the Java {@link Currency} class. Refer to the {@link Currency}
     * JavaDoc for more info on supported codes and thrown exceptions.
     * @see Currency
     */
    public static CurrencyPair fromIsoCodes(String baseCode, String quoteCode) {
        Currency base = Currency.getInstance(baseCode);
        Currency quote = Currency.getInstance(quoteCode);
        return new CurrencyPair(base, quote);
    }

    /**
     * Checks if the given currency is either the base or the quote.
     *
     * @param currency a currency, {@code null} is allowed
     * @return {@code true} if the given currency is either the base or the quote, {@code false} if not or is {@code null}
     */
    public boolean involves(@Nullable Currency currency) {
        return base.equals(currency) || quote.equals(currency);
    }

    /**
     * Returns the "pip" that is standard for this currency pair.
     * <p>
     * A pip is the smallest whole unit price move that an exchange rate can make, based on forex market convention. “Pip” is an acronym for
     * "percentage in point" or "price interest point".
     * <p>
     * By default, for most currency pairs on the market, this value will be {@code 0.0001}. The exception to this rule is the Japanese Yen
     * (JPY), for which a pip of {@code 0.01} is used.
     * <p>
     * Simple example: consider an exchange rate of {@code EUR/USD 1.1793}. If the value becomes {@code 1.1794}, it has changed by one pip.
     *
     * @return {@code 0.01} if either of the currencies is a Japanese Yen, {@code 0.0001} otherwise
     * @see <a href="https://www.investopedia.com/terms/p/pip.asp">What Are Pips in Forex Trading, and What Is Their Value? - Investopedia</a>
     */
    public BigDecimal getPipDefinition() {
        return involves(JAPANESE_YEN) ? JAPANESE_YEN_PIP_DEFINITION : DEFAULT_PIP_DEFINITION;
    }

    /**
     * Returns a {@code /}-delimited concatenation of both currencies' ISO 4217 codes, such as {@code EUR/USD}.
     * This is an established industry convention for textual representation of currency pairs.
     *
     * @apiNote It's worth noting another convention: when the context is specifically a machine-to-machine communication (database, REST
     * API, FIX protocol, etc.), a currency pair is formatted as the concatenation of its ISO 4217 codes <b>without</b> the {@code /}
     * delimiter, such as {@code EURUSD}. The {@code /}-delimited option is by an industry convention the "human-readable" one, used by
     * default for articles, reports and UI. The {@link Object#toString()} API contract specifies that this method should return an
     * <i>"informative representation that is easy for a person to read"</i>, therefore, this method uses the {@code /}-delimited format.
     * This also implies that this method returns a {@link String} that is suitable for UI or logs in a locale-agnostic manner.
     */
    @Override
    public String toString() {
        return base.getCurrencyCode() + '/' + quote.getCurrencyCode();
    }
}
