// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import jakarta.annotation.Nonnull;

// Utilities for the implementation of Strict* components.
final class StrictComponents { // Package-private

    private StrictComponents() {
        throw new UnsupportedOperationException();
    }

    static void checkOptionCount(int minCount, @Nonnull Object[] options) {
        if (options.length < minCount) { // Implicit null-check on the array
            throw new IllegalArgumentException("Must supply at least " + minCount + " options");
        }
    }
}
