// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;

public class FailedRemoteDataFetchException extends FailedDataFetchException {

    public FailedRemoteDataFetchException(String message) {
        super(message);
    }

    public FailedRemoteDataFetchException(Throwable cause) {
        super(cause);
    }
}
