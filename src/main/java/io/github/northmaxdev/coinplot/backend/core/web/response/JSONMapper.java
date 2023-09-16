// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;

import java.io.IOException;

@FunctionalInterface
public interface JSONMapper<D> {

    @Nonnull D map(@Nonnull byte[] json, @Nonnull ObjectMapper jsonParser) throws IOException;
}
