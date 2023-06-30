// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.response;

public final class DTOMappingException extends RuntimeException {

    public DTOMappingException(String message) {
        super(message);
    }

    public DTOMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
