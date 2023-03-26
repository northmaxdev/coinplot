// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import jakarta.annotation.Nonnull;

public record Currency(@Nonnull String threeLetterISOCode, @Nonnull String name) {}
