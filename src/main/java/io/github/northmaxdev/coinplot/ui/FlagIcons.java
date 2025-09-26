// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.html.Image;

import java.util.Currency;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * Static utility class for stuff related to country flag icons (e.g., factory methods).
 */
public final class FlagIcons {

    private static final String ICON_PROVIDER_BASE_URL = "https://flagcdn.com/";

    private FlagIcons() {
        throw new UnsupportedOperationException();
    }

    // TODO for when I next look at this file:
    //  1. Add only two public overloads for create(): Locale & Currency
    //  2. The create(String) method should be a private impl detail. The validation logic for 2-char country codes is too much (you also
    //  need to check that the random 2-char string is an actual recognized country code), and it's also a reinvention of the wheel because
    //  Locale and Currency already do it for us.

    public static Image create(Locale locale) {
        throw new UnsupportedOperationException();
    }

    public static Image create(Currency currency) {
        requireNonNull(currency);

        // The first two characters of a currency code are always an ISO 3166 country code - this is part of the ISO 4217 spec.
        // https://www.iso.org/iso-4217-currency-codes.html
        String iso31661alpha2Code = currency.getCurrencyCode().substring(0, 2);
        return createFromValidCountryCode(iso31661alpha2Code);
    }

    private static Image createFromValidCountryCode(String iso31661alpha2Code) {
        // flagcdn URLs are case-sensitive and accept only lowercase country codes.
        // This is where we call toLowerCase() on iso31661alpha2Code (not in create(Locale) or create(Currency)), because up until this
        // point we don't care about the casing.
        String uri = ICON_PROVIDER_BASE_URL + iso31661alpha2Code.toLowerCase() + ".svg";
        return new Image(uri, null);
    }
}
