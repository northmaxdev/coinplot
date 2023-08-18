// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import java.util.Optional;

@FunctionalInterface
public interface AccessKeySupplier {

    Optional<String> getAccessKey();
}
