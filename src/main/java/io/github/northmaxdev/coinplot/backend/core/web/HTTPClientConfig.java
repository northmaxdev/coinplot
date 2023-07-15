// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;

@Configuration
public class HTTPClientConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(Version.HTTP_2)
                .build();
    }
}
