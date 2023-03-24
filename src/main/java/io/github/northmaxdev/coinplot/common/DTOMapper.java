// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common;

import jakarta.annotation.Nonnull;

@FunctionalInterface
public interface DTOMapper<D, M> {

    @Nonnull M map(@Nonnull D dto);
}
