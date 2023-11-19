// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Optional;

// "Pseudo-abstract"
public abstract class VisualizerTab<T, C extends Component & Visualizer<T>>
        extends Tab
        implements Visualizer<T>, LocaleChangeObserver {

    private final C content;

    protected VisualizerTab(@Nonnull C visualizerComponent) {
        this.content = Objects.requireNonNull(visualizerComponent);

        add(content);
    }

    protected abstract @Nonnull String getLabelPropertyKey();

    @Override
    public final void visualize(@Nonnull T obj) {
        content.visualize(obj);
    }

    @Override
    public final void visualize(Optional<T> optional) {
        content.visualize(optional);
    }

    @Override
    public final void clear() {
        content.clear();
    }

    @Override
    public final void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setLabel(this, event, getLabelPropertyKey());

        if (content instanceof LocaleChangeObserver localeSensitiveContent) {
            localeSensitiveContent.localeChange(event);
        }
    }
}
