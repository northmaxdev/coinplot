// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Map;

import static java.util.Map.entry;

public final class HumanReadableDisplayNameGenerator implements DisplayNameGenerator {

    private static final Map<String, String> MAGIC_METHOD_NAMES = Map.ofEntries(
            entry("eq", "equals/hashCode contract"),
            entry("reqURI", "APIRequest actual URI equals to expected"),
            entry("mapsDTO", "DTO properly maps to a model representation")
    );

    @Override
    public String generateDisplayNameForClass(Class<?> aClass) {
        String testClassName = aClass.getSimpleName();

        int suffixIndex = testClassName.indexOf("Tests");
        if (suffixIndex == -1) {
            return markedAsIs(testClassName);
        }

        return "Tests for: [" + testClassName.substring(0, suffixIndex) + ']';
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> aClass) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> aClass, Method method) {
        String methodName = method.getName();
        return MAGIC_METHOD_NAMES.getOrDefault(methodName, markedAsIs(methodName));
    }

    private static String markedAsIs(String s) {
        // There's no conventional syntax for the purpose of this method; Just do whatever.
        return '<' + s + '>';
    }
}
