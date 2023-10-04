// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.web.request.CustomHostSupplier;
import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.Optional;

@Configuration
class FrankfurterConfiguration implements CustomHostSupplier { // Package-private

    @Value("${frankfurter.custom-host}")
    private @Nullable String customHostLiteral = null;

    @Override
    public Optional<HttpHost> getCustomHost() {
        // FIXME (Implementation): Come up with a better solution because this might be a potential bottleneck
        return Strings.blankToOptional(customHostLiteral)
                .map(s -> {
                    try {
                        return HttpHost.create(s);
                    } catch (URISyntaxException e) {
                        return null;
                    }
                });
    }
}
