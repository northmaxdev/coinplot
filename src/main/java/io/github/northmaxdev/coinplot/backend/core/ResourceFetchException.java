// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

public final class ResourceFetchException extends RuntimeException {

    public ResourceFetchException(String message) {
        super(message);
    }

    public ResourceFetchException(Throwable cause) {
        super(cause);
    }
}
