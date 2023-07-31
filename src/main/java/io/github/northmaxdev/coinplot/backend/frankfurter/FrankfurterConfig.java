// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.web.CustomHostConfig;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class FrankfurterConfig implements CustomHostConfig {

    // TODO: WIP

    @Override
    public Optional<HttpHost> getCustomHost() {
        return Optional.empty();
    }
}
