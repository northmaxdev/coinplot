// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.lang.Strings;
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
            entry("mapsDTO", "DTO maps to a model representation as expected"),

            // Repository stuff
            entry("repoEmpty", "isEmpty returns true on empty repository"),
            entry("repoNonEmpty", "isEmpty returns false on non-empty repository"),
            entry("findAllAsSetOnEmpty", "findAllAsSet returns empty Set on empty repository"),
            entry("findAllAsSetOnNonEmpty", "findAllAsSet returns non-empty Set on non-empty repository")
    );

    @Override
    public String generateDisplayNameForClass(Class<?> aClass) {
        String testClassName = aClass.getSimpleName();
        return Strings.substringBefore(testClassName, "Tests")
                .map(s -> "Tests for: [" + s + ']')
                .orElse(markedAsIs(testClassName));
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> aClass) {
        // The convention in this project is to use nested test classes for nested production classes.
        // In this case, the semantic difference between a top-level test class and a nested test class is none.
        return generateDisplayNameForClass(aClass);
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> aClass, Method method) {
        String testMethodName = method.getName();
        return MAGIC_TEST_METHOD_NAMES.getOrDefault(testMethodName, markedAsIs(testMethodName));
    }

    private static String markedAsIs(String s) {
        // There's no conventional syntax for the purpose of this method; Just do whatever.
        return '<' + s + '>';
    }
}
