// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.util;

import java.util.Locale;

public final class Locales {

    // Some locales are not predefined in the JDK, so they are here.
    // Some locales already are predefined in the JDK, but are given a more descriptive name here.
    public static final Locale ENGLISH_UK = Locale.of("en", "GB");
    public static final Locale ENGLISH_US = Locale.of("en", "US");
    public static final Locale HEBREW_ISRAEL = Locale.of("he", "IL");
    public static final Locale RUSSIAN_RUSSIA = Locale.of("ru", "RU");

    private Locales() {
        throw new UnsupportedOperationException();
    }
}
