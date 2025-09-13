// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.domain.FrankfurterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Locale;

@EnableConfigurationProperties({FrankfurterConfig.class})
@SpringBootApplication
public class CoinPlotApp {

    public static final Locale COINPLOT_LOCALE = Locale.UK; // This is the closest thing to Microsoft's "English (International)"

    public static void main(String[] args) {
        Locale.setDefault(COINPLOT_LOCALE);
        SpringApplication.run(CoinPlotApp.class, args);
    }
}
