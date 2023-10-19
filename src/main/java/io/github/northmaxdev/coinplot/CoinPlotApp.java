// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.backend.frankfurter.FrankfurterConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(FrankfurterConfiguration.class)
@SpringBootApplication
public class CoinPlotApp {

    public static void main(String[] args) {
        SpringApplication.run(CoinPlotApp.class, args);
    }
}
