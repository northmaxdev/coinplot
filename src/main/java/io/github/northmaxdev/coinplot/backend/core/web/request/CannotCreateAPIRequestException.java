// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class CannotCreateAPIRequestException extends Exception { // Non-final

    private static final String NO_ACCESS_KEY_MESSAGE = "No access key";

    public CannotCreateAPIRequestException(@Nullable String message) {
        super(message);
    }

    public static @Nonnull CannotCreateAPIRequestException forNoAccessKey() {
        return new CannotCreateAPIRequestException(NO_ACCESS_KEY_MESSAGE);
    }
}
