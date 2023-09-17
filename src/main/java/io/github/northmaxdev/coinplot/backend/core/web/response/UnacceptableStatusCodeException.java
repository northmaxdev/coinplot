// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.response;

import jakarta.annotation.Nullable;

public final class UnacceptableStatusCodeException extends Exception {

    private final int statusCode;

    public UnacceptableStatusCodeException(int statusCode) {
        this(statusCode, "Unacceptable status code: " + statusCode);
    }

    public UnacceptableStatusCodeException(int statusCode, @Nullable String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
