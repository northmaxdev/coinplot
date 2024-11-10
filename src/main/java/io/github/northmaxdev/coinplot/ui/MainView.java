// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.Direction;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.domain.ExchangeRatesService;
import io.github.northmaxdev.coinplot.ui.panels.ExchangeRatesDashboard;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PageTitle("CoinPlot")
public final class MainView extends AppLayout {

    @Autowired
    public MainView(ExchangeRatesService exchangeRatesService) {
        // TODO:
        //  If instantiation of nested components throws a RuntimeException,
        //  set MainView content to an error message with a stack trace.
        // TODO: Favicon
        // TODO: Application-wide exception handler (error dialog or a separate view)
        UI.getCurrent().setDirection(Direction.RIGHT_TO_LEFT);

        //////////////////
        // Main content //
        //////////////////

        ExchangeRatesDashboard dashboard = new ExchangeRatesDashboard(exchangeRatesService);
        setContent(dashboard);

        /////////////
        // Top bar //
        /////////////

        Button sourceCodeButton = new Button("GitHub", VaadinIcon.CURLY_BRACKETS.create());
        sourceCodeButton.addClickListener(_ -> getUI()
                .map(UI::getPage)
                .ifPresent(page -> page.open("https://github.com/northmaxdev/coinplot")));
        sourceCodeButton.setTooltipText("תוכנה עם קוד פתוח באהבה 😊");

        HorizontalLayout topBar = new HorizontalLayout(FlexComponent.Alignment.CENTER, sourceCodeButton);
        topBar.setPadding(true);
        addToNavbar(topBar);
    }
}
