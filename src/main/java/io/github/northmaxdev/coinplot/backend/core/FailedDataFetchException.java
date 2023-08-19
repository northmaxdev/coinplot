// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import jakarta.annotation.Nullable;

public class FailedDataFetchException extends RuntimeException { // Non-final

    public FailedDataFetchException(@Nullable String message) {
        super(message);
    }

    public FailedDataFetchException(@Nullable Throwable cause) {
        super(cause);
    }
}
