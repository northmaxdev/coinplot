// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Map;

import static java.util.Map.entry;

public final class HumanReadableDisplayNameGenerator implements DisplayNameGenerator {

    private static final Map<String, String> MAGIC_TEST_METHOD_NAMES = Map.ofEntries(
            // Standard Java stuff
            entry("eq", "equals/hashCode contract"),
            // APIRequest stuff
            entry("reqURI", "APIRequest actual URI is contained in expected outcomes"),
            entry("reqHeaders", "APIRequest actual headers equal to expected"),
            // DTO stuff
            entry("mapsDTO", "DTO maps to a model representation as expected")
    );

    @Override
    public @Nonnull String generateDisplayNameForClass(@Nonnull Class<?> aClass) {
        // Explicit null-checks are not needed here
        String testClassName = aClass.getSimpleName();
        return Strings.substringBefore(testClassName, "Tests")
                .map(s -> "Tests for: [" + s + ']')
                .orElse(markedUnknown(testClassName));
    }

    @Override
    public @Nonnull String generateDisplayNameForNestedClass(@Nonnull Class<?> aClass) {
        // The convention in this project is to use nested test classes for nested production classes.
        // In this case, the semantic difference between a top-level test class and a nested test class is none.
        return generateDisplayNameForClass(aClass);
    }

    @Override
    public @Nonnull String generateDisplayNameForMethod(@Nonnull Class<?> aClass, @Nonnull Method method) {
        // Explicit null-checks are not needed here
        String testMethodName = method.getName();
        return MAGIC_TEST_METHOD_NAMES.getOrDefault(testMethodName, markedUnknown(testMethodName));
    }

    private static @Nonnull String markedUnknown(@Nullable String name) {
        return "(!) " + name;
    }
}
