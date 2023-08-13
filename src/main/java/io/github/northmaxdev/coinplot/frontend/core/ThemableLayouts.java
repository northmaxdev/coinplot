// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.ThemeList;

public final class ThemableLayouts {

    public enum Spacing {
        EXTRA_SMALL("spacing-xs"),
        SMALL("spacing-s"),
        MEDIUM("spacing"),
        LARGE("spacing-l"),
        EXTRA_LARGE("spacing-xl");

        private final String value;

        Spacing(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private ThemableLayouts() {
        throw new UnsupportedOperationException();
    }

    public static HorizontalLayout createHorizontal(Spacing spacing, Component... children) {
        HorizontalLayout layout = new HorizontalLayout(children);

        layout.setSpacing(false); // Remove default setting (the Vaadin component docs do this themselves)
        ThemeList themeList = layout.getThemeList();
        themeList.add(spacing.toString());

        return layout;
    }
}
