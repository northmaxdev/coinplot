// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

// Semantically not a @FunctionalInterface
public interface RequiresAuthentication {

    boolean canAuthenticate();

    default boolean cannotAuthenticate() {
        return !canAuthenticate();
    }
}
