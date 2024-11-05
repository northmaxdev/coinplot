// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.util.Strings;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public final class DescriptiveDisplayNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(Class<?> c) {
        String className = c.getSimpleName();
        return Strings.substringBefore(className, "Tests")
                .map(s -> "Tests for: [" + s + ']')
                .orElse(markedWithWarning(className));
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> c) {
        // FIXME: Nested test classes currently don't have a proper convention for display names
        return markedWithWarning(c.getSimpleName());
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> c, Method method) {
        String methodName = method.getName();
        return switch (methodName) {
            case "eq" -> "equals and hashCode contract";
            case "stream" -> "stream produces expected contents";
            default -> markedWithWarning(methodName);
        };
    }

    private static String markedWithWarning(String s) {
        return "⚠️ " + s;
    }
}
