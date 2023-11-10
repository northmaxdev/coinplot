// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Locale;

@FunctionalInterface
public interface Localizable {

    @Nonnull String toLocalizedString(@Nonnull Locale locale);
}
