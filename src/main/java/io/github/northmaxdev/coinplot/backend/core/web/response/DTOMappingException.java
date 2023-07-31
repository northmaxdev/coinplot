// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.response;

public class DTOMappingException extends RuntimeException {

    public DTOMappingException(String message) {
        super(message);
    }

    public DTOMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
