// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Locale;
import java.util.Objects;

public interface TextuallyDisplayable {

    // This method is conceptually similar to Object::toString, with the exception that
    // the latter is a general-purpose textual representation, which is usually used for
    // logging and debugging, but not necessarily for UI. As such, this method provides
    // a stricter contract, ensuring that the resulting character string is appropriate
    // for usage in user-facing scenarios, which may include CLI, GUI, admin logs, etc.
    // Sometimes, a class has a "natural" toString implementation (as in "natural order"),
    // which might be acceptable for getDisplayName as well. As mentioned previously,
    // this interface exists primarily to provide an API contract.
    @Nonnull String getDisplayName();

    // Implementations may often not be able to provide a localized display name.
    // For this reason, the default implementation is to return the locale-agnostic one.
    default @Nonnull String getDisplayName(@Nonnull Locale locale) {
        Objects.requireNonNull(locale);
        return getDisplayName();
    }
}
