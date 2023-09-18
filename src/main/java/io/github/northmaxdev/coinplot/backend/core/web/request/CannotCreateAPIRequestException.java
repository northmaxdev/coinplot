// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class CannotCreateAPIRequestException extends Exception {

    public CannotCreateAPIRequestException(@Nullable String message) {
        super(message);
    }

    public static @Nonnull CannotCreateAPIRequestException forNoAccessKey() {
        return new CannotCreateAPIRequestException("No access key");
    }
}
