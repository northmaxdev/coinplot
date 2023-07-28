// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

public interface RequiresAuthentication {

    boolean canAuthenticate();

    default boolean cannotAuthenticate() {
        return !canAuthenticate();
    }
}
