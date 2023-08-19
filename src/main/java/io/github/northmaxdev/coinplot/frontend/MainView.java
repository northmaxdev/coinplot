// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.backend.core.EverapiDataProvider;
import io.github.northmaxdev.coinplot.backend.core.FixerDataProvider;
import io.github.northmaxdev.coinplot.backend.core.FrankfurterDataProvider;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PageTitle("CoinPlot")
public final class MainView extends AppLayout {

    @Autowired
    public MainView(
            @Nonnull EverapiDataProvider everapi,
            @Nonnull FixerDataProvider fixer,
            @Nonnull FrankfurterDataProvider frankfurter) {
    }
}
