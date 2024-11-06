// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.domain.currency.FrankfurterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({FrankfurterConfig.class})
@SpringBootApplication
public class CoinPlotApp {

    public static void main(String[] args) {
        SpringApplication.run(CoinPlotApp.class, args);
    }
}
