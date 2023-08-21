// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.time.Duration;

@Configuration
public class HTTPClientConfiguration {

    private static final Duration CONNECT_TIMEOUT = Duration.ofMinutes(2);

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(CONNECT_TIMEOUT)
                // NOTE: The redirect thing is here only in the hopes that this will
                // eliminate the chance of an HttpResponse::body returning null, which
                // is NOT accounted for in AbstractRemoteDataFetchService::fetch
                .followRedirects(HttpClient.Redirect.NEVER)
                .version(Version.HTTP_2)
                .build();
    }
}
