// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.common.web;

import jakarta.annotation.Nonnull;

@FunctionalInterface
public interface DTOMapper<D, M> {

    @Nonnull M map(@Nonnull D dto) throws DTOMappingException;
}
