// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.web.request.CustomHostSupplier;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class FrankfurterConfiguration implements CustomHostSupplier {

    // TODO: Implement this

    @Override
    public Optional<HttpHost> getCustomHost() {
        return Optional.empty();
    }
}
