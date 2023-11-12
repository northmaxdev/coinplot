// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

public interface TextuallyDisplayable {

    // In some cases, toString is already suitable for front-facing display purposes.
    // This method should be overridden otherwise.
    default @Nonnull String toDisplayString() {
        return toString();
    }
}
