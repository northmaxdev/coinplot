// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import java.util.Optional;

@FunctionalInterface
public interface AccessKeyConfiguration {

    Optional<String> getAccessKey();
}
