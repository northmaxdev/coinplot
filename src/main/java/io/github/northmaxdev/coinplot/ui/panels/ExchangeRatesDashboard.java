// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.panels;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import io.github.northmaxdev.coinplot.domain.ExchangeRatesService;

public final class ExchangeRatesDashboard extends SplitLayout {

    // TODO: Primary to secondary ratio must be 2-to-1 (currently it's 50/50)

    public ExchangeRatesDashboard(ExchangeRatesService dataSource) {
        super(Orientation.HORIZONTAL);

        /////////////
        // Primary //
        /////////////

        ExchangeRatesPlot plot = new ExchangeRatesPlot();

        Button plotExportButton = new Button("ייצוא", VaadinIcon.DOWNLOAD.create());
        plotExportButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        plotExportButton.addSingleClickListener(_ -> {
            // TODO: SVG export
        });

        ProgressBar plotProgressBar = new ProgressBar();
        plotProgressBar.setHeight(30, Unit.PERCENTAGE); // This looks kinda better, the default is too thin

        HorizontalLayout plotUtilBar = new HorizontalLayout(plotExportButton, plotProgressBar);
        plotUtilBar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToPrimary(wrap(plot, plotUtilBar));

        ///////////////
        // Secondary //
        ///////////////

        ExchangeRatesRequestForm form = new ExchangeRatesRequestForm(dataSource);
        form.setOnSubmit(exchangeBatch -> {
            // TODO:
            //  0. Disable form
            //  1. Enable progress bar
            //  2. Fetch data
            //  3. Plot data
            //  4. Disable progress bar
            //  5. Enable form
            //  Possible options for impl:
            //  1. CompletableFuture.runAsync
            //  2. Launch task on Java 21 virtual threads
        });

        addToSecondary(wrap(form));
    }

    private static VerticalLayout wrap(Component... components) {
        VerticalLayout box = new VerticalLayout(components);
        box.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        box.setSpacing(true);
        box.setPadding(true);
        return box;
    }
}
