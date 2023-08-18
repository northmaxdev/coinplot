// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.fn;

import jakarta.annotation.Nullable;

import java.util.function.Consumer;

public final class Consumers {

    private Consumers() {
        throw new UnsupportedOperationException();
    }

    // Imitation of Kotlin/C# null-safe method calls
    public static <T> void acceptIfNotNull(@Nullable Consumer<T> consumer, T param) {
        if (consumer != null) {
            consumer.accept(param);
        }
    }
}
