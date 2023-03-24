// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;

@Configuration
@PropertySource("classpath:api.properties")
public class APIConfig {

    @Value("${uri.base}")
    private String uriBase;

    public URI createCurrenciesEndpointURI() {
        // This cannot be pre-constructed as uriBase gets injected *after* the constructor. It *can* be cached, though.
        // However, since currency data is meant to be queried exactly once during an application's lifetime, this is
        // most likely not needed.
        return URI.create(uriBase + "currencies");
    }
}
