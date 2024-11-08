// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.util;

import java.util.Locale;

public final class Locales {

    // A constant not present in the JDK.
    // Same naming strategy as the JDK constants: if the locale specifies both a country and a language,
    // the country takes precedence for the variable name.
    public static final Locale ISRAEL = Locale.of("he", "IL");

    private Locales() {}
}
