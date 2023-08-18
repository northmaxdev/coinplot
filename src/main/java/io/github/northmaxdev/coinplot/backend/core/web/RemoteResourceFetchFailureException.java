// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;

public class RemoteResourceFetchFailureException extends FailedDataFetchException {

    public RemoteResourceFetchFailureException(String message) {
        super(message);
    }

    public RemoteResourceFetchFailureException(Throwable cause) {
        super(cause);
    }
}
