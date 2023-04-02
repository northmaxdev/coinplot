// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.web;

import java.io.IOException;

public final class DTOMappingException extends IOException {

    public DTOMappingException(String message) {
        super(message);
    }

    public DTOMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
