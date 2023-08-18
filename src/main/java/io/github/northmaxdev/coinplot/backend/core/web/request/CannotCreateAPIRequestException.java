// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

public class CannotCreateAPIRequestException extends IllegalStateException {

    public CannotCreateAPIRequestException(String message) {
        super(message);
    }

    public static CannotCreateAPIRequestException forNoAccessKey() {
        return new CannotCreateAPIRequestException("No access key");
    }
}
