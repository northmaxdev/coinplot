// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import jakarta.annotation.Nonnull;

import java.util.Collection;

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

    static void checkOptionCount(int minCount, @Nonnull Collection<?> options) {
        if (options.size() < minCount) { // Implicit null-check on the collection
            throw new IllegalArgumentException("Must supply at least " + minCount + " options");
        }
    }
}
