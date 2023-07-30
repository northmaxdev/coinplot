// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

public class ResourceFetchFailureException extends RuntimeException {

    public ResourceFetchFailureException(String message) {
        super(message);
    }

    public ResourceFetchFailureException(Throwable cause) {
        super(cause);
    }
}
