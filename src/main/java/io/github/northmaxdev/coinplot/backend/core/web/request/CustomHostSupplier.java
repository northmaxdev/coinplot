// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import org.apache.hc.core5.http.HttpHost;

import java.util.Optional;

@FunctionalInterface
public interface CustomHostSupplier {

    Optional<HttpHost> getCustomHost();
}
