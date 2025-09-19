// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import io.github.northmaxdev.coinplot.domain.ExchangeRatesService;

// TODO: Try the new Master-Detail layout (https://vaadin.com/docs/latest/components/master-detail-layout)
public final class ExchangeRatesDashboard extends SplitLayout {

    private static final double PRIMARY_CONTENT_SPLIT_PERCENTAGE = 65;

    public ExchangeRatesDashboard(ExchangeRatesService dataSource) {
        super(Orientation.HORIZONTAL);

        ExchangeRatesPlot plot = new ExchangeRatesPlot();
        ExchangeRatesRequestForm form = new ExchangeRatesRequestForm(dataSource);
        form.setOnSubmit(exchangeBatch -> {
            // TODO: Handle fetch exceptions
            var dataset = dataSource.getExchangeRates(exchangeBatch);
            plot.plot(dataset);
        });

        setSplitterPosition(PRIMARY_CONTENT_SPLIT_PERCENTAGE);
        addToPrimary(wrap(plot));
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
