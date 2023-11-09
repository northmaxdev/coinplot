// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.backend.core.DataProviderConfiguration;
import io.github.northmaxdev.coinplot.backend.fixer.FixerConfiguration;
import io.github.northmaxdev.coinplot.backend.frankfurter.FrankfurterConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        DataProviderConfiguration.class,
        FixerConfiguration.class,
        FrankfurterConfiguration.class
})
@SpringBootApplication
public class CoinPlotApp {

    public static void main(String[] args) {
        SpringApplication.run(CoinPlotApp.class, args);
    }
}
