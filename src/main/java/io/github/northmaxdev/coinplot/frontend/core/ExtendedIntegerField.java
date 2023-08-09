// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.textfield.IntegerField;
import io.github.northmaxdev.coinplot.lang.Ints;
import jakarta.annotation.Nullable;

import java.util.OptionalInt;

public final class ExtendedIntegerField extends IntegerField { // If only Java had extension methods :(

    public ExtendedIntegerField() {
        super();
    }

    public OptionalInt getOptionalIntValue() {
        @Nullable Integer i = getValue();
        return Ints.optionalOfNullable(i);
    }

    public int getValueOrZeroIfNull() {
        @Nullable Integer i = getValue();
        return Ints.zeroIfNull(i);
    }
}
