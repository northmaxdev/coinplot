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
import io.github.northmaxdev.coinplot.frontend.domain.exchange.ExchangeRateDataDashboard;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Route
@PageTitle("CoinPlot")
public final class MainView extends AppLayout {

    private static final class Header extends HorizontalLayout implements LocaleChangeObserver {

        private static final String UI_LOCALE_PICKER_LABEL_KEY = "main-view.header.ui-locale-picker.label";
        private static final String DATA_PROVIDER_DISPLAY_NAME_FIELD_LABEL_KEY = "main-view.header.data-provider-display-name-field.label";

        private final LocalePicker uiLocalePicker;
        private final TextField dataProviderDisplayNameField;

        public Header(@Nonnull I18NProvider i18nProvider, @Nonnull DataProvider dataProvider) {
            // Omit null-checks

            uiLocalePicker = new LocalePicker(i18nProvider.getProvidedLocales());
            uiLocalePicker.addValueChangeListener(event -> {
                @Nullable Locale selectedLocale = event.getValue();
                @Nullable UI currentUI = UI.getCurrent();
                // Not sure if the current UI being null is an actually realistic case
                if (selectedLocale != null && currentUI != null) {
                    currentUI.setLocale(selectedLocale);
                }
            });

            dataProviderDisplayNameField = new TextField();
            dataProviderDisplayNameField.setValue(dataProvider.getDisplayName());
            dataProviderDisplayNameField.setReadOnly(true);
            dataProviderDisplayNameField.setPrefixComponent(VaadinIcon.DATABASE.create());

            add(uiLocalePicker, dataProviderDisplayNameField);
            setPadding(true);
            setSpacing(true);
        }

        @Override
        public void localeChange(@Nonnull LocaleChangeEvent event) {
            I18NUtilities.setLabel(uiLocalePicker, event, UI_LOCALE_PICKER_LABEL_KEY);
            I18NUtilities.setLabel(dataProviderDisplayNameField, event, DATA_PROVIDER_DISPLAY_NAME_FIELD_LABEL_KEY);
        }
    }

    @Autowired
    public MainView(@Nonnull DataProviderService dataProviderService, @Nonnull I18NProvider i18nProvider) {
        DataProvider dataProvider = dataProviderService.getSelectedProviderOrDefault();

        ExchangeRateDataDashboard dashboard = new ExchangeRateDataDashboard(dataProvider);
        setContent(dashboard);

        Header header = new Header(i18nProvider, dataProvider);
        addToNavbar(header);

        setPrimarySection(Section.NAVBAR);
    }
}
