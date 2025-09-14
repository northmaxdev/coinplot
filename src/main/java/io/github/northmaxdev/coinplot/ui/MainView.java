// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.CoinPlotApp;
import io.github.northmaxdev.coinplot.domain.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PageTitle("CoinPlot")
public final class MainView extends AppLayout {

    // TODO: Application-wide exception handler (error dialog or a separate view)

    @Autowired
    public MainView(ExchangeRatesService exchangeRatesService) {

        //--------------//
        // Main content //
        //--------------//

        ExchangeRatesDashboard dashboard = new ExchangeRatesDashboard(exchangeRatesService);
        setContent(dashboard);

        //---------//
        // Top bar //
        //---------//

        TextField localeDisplayField = new TextField("Language");
        localeDisplayField.setValue(CoinPlotApp.MAIN_LOCALE.getDisplayName());
        localeDisplayField.setMinWidth(70, Unit.PERCENTAGE);
        localeDisplayField.setReadOnly(true);
        localeDisplayField.setPrefixComponent(VaadinIcon.GLOBE.create());
        localeDisplayField.setTooltipText("Well, if this is it, old boy, I hope you don't mind if I go out speaking the King's.");

        Button sourceCodeButton = new Button("Source code", VaadinIcon.CURLY_BRACKETS.create());
        sourceCodeButton.addClickListener(_ -> getUI()
                .map(UI::getPage)
                .ifPresent(page -> page.open("https://github.com/northmaxdev/coinplot")));

        HorizontalLayout topBar = new HorizontalLayout(FlexComponent.Alignment.END, localeDisplayField, sourceCodeButton);
        topBar.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        topBar.setPadding(true);
        addToNavbar(topBar);
    }
}
