// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.util.Objects;

@FunctionalInterface
public interface JSONMapper<D> {

    @Nonnull D map(@Nonnull byte[] json, @Nonnull ObjectMapper jsonParser) throws IOException;

    static <D> @Nonnull JSONMapper<D> forClass(@Nonnull Class<D> dtoClass) {
        Objects.requireNonNull(dtoClass);
        return (json, parser) -> {
            Objects.requireNonNull(json);
            Objects.requireNonNull(parser);
            return parser.readValue(json, dtoClass);
        };
    }

    static <D> @Nonnull JSONMapper<D> forTypeReference(@Nonnull TypeReference<D> typeReference) {
        Objects.requireNonNull(typeReference);
        return (json, parser) -> {
            Objects.requireNonNull(json);
            Objects.requireNonNull(parser);
            return parser.readValue(json, typeReference);
        };
    }
}
