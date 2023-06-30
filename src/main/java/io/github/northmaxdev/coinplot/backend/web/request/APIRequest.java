// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.web.request;

import jakarta.annotation.Nonnull;

import java.net.URI;

// Not marked @FunctionalInterface as this is *semantically* not a functional interface.
// Additional methods may be added in the future.
public interface APIRequest {

    @Nonnull URI toURI();
}
