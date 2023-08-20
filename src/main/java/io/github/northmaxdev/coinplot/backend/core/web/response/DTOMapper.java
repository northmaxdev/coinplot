// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.response;

import jakarta.annotation.Nonnull;

@FunctionalInterface
public interface DTOMapper<D, M> {

    // The nullability of 'M' is implementation-specific
    M map(@Nonnull D dto) throws DTOMappingException;
}
