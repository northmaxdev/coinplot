// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.backend.core.DataProviderService;
import io.github.northmaxdev.coinplot.frontend.common.LocalePicker;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Route
@PageTitle("CoinPlot")
public final class MainView extends AppLayout {

    @Autowired
    public MainView(@Nonnull DataProviderService dataProviderService, @Nonnull I18NProvider i18nProvider) {
        DataProvider dataProvider = dataProviderService.getSelectedProviderOrDefault();

        ////////////
        // Navbar //
        ////////////
        LocalePicker localePicker = LocalePicker.fromI18NProvider(i18nProvider);
        localePicker.addValueChangeListener(event -> {
            @Nullable Locale locale = event.getValue();
            @Nullable UI currentUI = UI.getCurrent();
            if (locale != null && currentUI != null) {
                currentUI.setLocale(locale);
            }
        });

        HorizontalLayout navbarContent = new HorizontalLayout(localePicker);
        navbarContent.setJustifyContentMode(FlexComponent.JustifyContentMode.END); // FIXME: This doesn't work for some reason
        navbarContent.setSpacing(true);
        navbarContent.setPadding(true);

        addToNavbar(navbarContent);
        setPrimarySection(Section.NAVBAR);
    }
}
