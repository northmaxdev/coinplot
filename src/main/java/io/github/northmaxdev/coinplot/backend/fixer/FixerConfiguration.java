// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.web.request.AccessKeySupplier;
import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class FixerConfiguration implements AccessKeySupplier {

    @Value("${fixer.access-key}")
    private @Nullable String accessKeyValue = null;

    @Override
    public Optional<String> getAccessKey() {
        return Strings.blankToOptional(accessKeyValue);
    }
}
