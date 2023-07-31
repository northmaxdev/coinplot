// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import org.apache.hc.core5.http.HttpHost;

import java.util.Optional;

@FunctionalInterface
public interface CustomHostConfiguration {

    Optional<HttpHost> getCustomHost();
}
