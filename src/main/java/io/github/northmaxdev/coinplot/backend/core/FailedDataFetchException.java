// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

public class FailedDataFetchException extends RuntimeException {

    public FailedDataFetchException(String message) {
        super(message);
    }

    public FailedDataFetchException(Throwable cause) {
        super(cause);
    }
}
