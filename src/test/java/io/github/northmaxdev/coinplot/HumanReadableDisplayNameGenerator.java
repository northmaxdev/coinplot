// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public final class HumanReadableDisplayNameGenerator implements DisplayNameGenerator {

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
        throw new UnsupportedOperationException(); // TODO
    }

    private String markedAsIs(String s) {
        // There's no conventional syntax for the purpose of this method; Just do whatever.
        return '<' + s + '>';
    }
}
