// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.backend.core.FixerDataProvider;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PageTitle("CoinPlot")
public final class MainView extends AppLayout {

    @Autowired
    public MainView(@Nonnull FixerDataProvider fixer) {
    }
}
