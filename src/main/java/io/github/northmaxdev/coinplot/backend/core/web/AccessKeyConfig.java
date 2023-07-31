// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import java.util.Optional;

// Semantically not a @FunctionalInterface
public interface AccessKeyConfig {

    Optional<String> getAccessKey();
}
