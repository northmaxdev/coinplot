// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.backend.core.DataProviderService;
import io.github.northmaxdev.coinplot.frontend.common.LocalePicker;
import io.github.northmaxdev.coinplot.frontend.domain.exchange.ExchangeRateAnalyticsDashboard;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;
import java.util.Objects;

@Route
@PageTitle("CoinPlot")
public final class MainView extends AppLayout {

    private static final class Header extends HorizontalLayout implements LocaleChangeObserver {

        private final LocalePicker uiLocalePicker;
        private final TextField dataProviderDisplayField;

        public Header(@Nonnull I18NProvider i18nProvider, @Nonnull DataProvider dataProvider) {
            uiLocalePicker = new LocalePicker(i18nProvider.getProvidedLocales());
            uiLocalePicker.addValueChangeListener(event -> {
                @Nullable Locale selectedLocale = event.getValue();
                @Nonnull UI currentUI = Objects.requireNonNull(UI.getCurrent(), "Current UI is null");
                if (selectedLocale != null) {
                    currentUI.setLocale(selectedLocale);
                }
            });

            dataProviderDisplayField = new TextField();
            dataProviderDisplayField.setValue(dataProvider.getDisplayName());
            dataProviderDisplayField.setReadOnly(true);
            dataProviderDisplayField.setPrefixComponent(VaadinIcon.DATABASE.create());

            add(uiLocalePicker, dataProviderDisplayField);
            setPadding(true);
            setSpacing(true);
        }

        @Override
        public void localeChange(@Nonnull LocaleChangeEvent event) {
            I18NUtilities.setLabel(uiLocalePicker, event, "main-view.header.ui-locale-picker.label");
            I18NUtilities.setLabel(dataProviderDisplayField, event, "main-view.header.data-provider-display-field.label");
        }
    }

    @Autowired
    public MainView(@Nonnull DataProviderService dataProviderService, @Nonnull I18NProvider i18nProvider) {
        DataProvider dataProvider = dataProviderService.getSelectedProviderOrDefault();

        var dashboard = new ExchangeRateAnalyticsDashboard(dataProvider);
        setContent(dashboard);

        var header = new Header(i18nProvider, dataProvider);
        addToNavbar(header);
    }
}
