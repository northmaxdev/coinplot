// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;

import java.net.URI;
import java.util.Map;

public interface APIRequest {

    @Nonnull URI getURI();

    @Nonnull Map<String, String> getHeaders();
}
