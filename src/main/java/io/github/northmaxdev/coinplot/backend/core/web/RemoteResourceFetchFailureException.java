// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import io.github.northmaxdev.coinplot.backend.core.ResourceFetchFailureException;

public class RemoteResourceFetchFailureException extends ResourceFetchFailureException {

    public RemoteResourceFetchFailureException(String message) {
        super(message);
    }

    public RemoteResourceFetchFailureException(Throwable cause) {
        super(cause);
    }
}
