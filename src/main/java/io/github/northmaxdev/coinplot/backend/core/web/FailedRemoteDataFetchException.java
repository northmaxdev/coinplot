// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import jakarta.annotation.Nullable;

public class FailedRemoteDataFetchException extends FailedDataFetchException { // Non-final

    public FailedRemoteDataFetchException(@Nullable String message) {
        super(message);
    }

    public FailedRemoteDataFetchException(@Nullable Throwable cause) {
        super(cause);
    }
}
