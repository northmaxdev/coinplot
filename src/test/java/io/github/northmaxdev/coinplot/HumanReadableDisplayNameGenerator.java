// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.lang.Strings;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Map;

import static java.util.Map.entry;

public final class HumanReadableDisplayNameGenerator implements DisplayNameGenerator {

    private static final Map<String, String> MAGIC_TEST_METHOD_NAMES = Map.ofEntries(
            entry("eq", "equals/hashCode contract"),
            entry("reqURI", "APIRequest actual URI is contained in expected outcomes"),
            entry("reqHeaders", "APIRequest actual headers equal to expected"),
            entry("mapsDTO", "DTO maps to a model representation as expected")
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
        throw new UnsupportedOperationException();
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
