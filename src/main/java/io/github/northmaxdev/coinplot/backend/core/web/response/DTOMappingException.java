// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.response;

import jakarta.annotation.Nullable;

public class DTOMappingException extends Exception { // Non-final

    public DTOMappingException(@Nullable String message) {
        super(message);
    }

    public DTOMappingException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
