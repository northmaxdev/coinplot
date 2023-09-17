// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.util.Objects;

@FunctionalInterface
public interface JSONParsingStrategy<T> {

    @Nonnull T apply(@Nonnull byte[] json, @Nonnull ObjectMapper jsonParser) throws IOException;

    static <T> @Nonnull JSONParsingStrategy<T> usingClass(@Nonnull Class<T> aClass) {
        Objects.requireNonNull(aClass);
        return (json, jsonParser) -> {
            Objects.requireNonNull(json);
            Objects.requireNonNull(jsonParser);
            return jsonParser.readValue(json, aClass);
        };
    }

    static <T> @Nonnull JSONParsingStrategy<T> usingTypeReference(@Nonnull TypeReference<T> typeReference) {
        Objects.requireNonNull(typeReference);
        return (json, jsonParser) -> {
            Objects.requireNonNull(json);
            Objects.requireNonNull(jsonParser);
            return jsonParser.readValue(json, typeReference);
        };
    }
}
