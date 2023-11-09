// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.springext;

import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@ConfigurationPropertiesBinding
public final class HttpHostConverter implements Converter<String, HttpHost> {

    @Override
    public @Nullable HttpHost convert(@Nullable String source) {
        if (Strings.isNullOrBlank(source)) {
            return null;
        }

        try {
            return HttpHost.create(source);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid content for HttpHost: " + source, e);
        }
    }
}
