// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.web.request.CustomHostSupplier;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.Optional;

@Configuration
public class FrankfurterConfiguration implements CustomHostSupplier {

    @Value("${frankfurter.custom-host}")
    private @Nullable String customHostLiteral = null;

    @Override
    public Optional<HttpHost> getCustomHost() {
        // FIXME: Come up with a better solution because this might be a potential bottleneck
        return Optional.ofNullable(customHostLiteral)
                .map(s -> {
                    try {
                        return HttpHost.create(s);
                    } catch (URISyntaxException e) {
                        return null;
                    }
                });
    }
}
