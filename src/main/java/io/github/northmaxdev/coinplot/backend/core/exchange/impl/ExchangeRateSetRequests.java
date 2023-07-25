// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange.impl;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.lang.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public final class ExchangeRateSetRequests {

    public static final class ParametersBuilder {

        private final @Nonnull ExchangeRateSetRequest request;
        private @Nullable String baseParameterName;
        private @Nullable String targetsParameterName;
        private @Nullable String startParameterName;
        private @Nullable String endParameterName;
        private @Nonnull DateTimeFormatter dateFormatter;

        public ParametersBuilder(@Nonnull ExchangeRateSetRequest request) {
            this.request = request;
            this.baseParameterName = null;
            this.targetsParameterName = null;
            this.startParameterName = null;
            this.endParameterName = null;
            this.dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        }

        public ParametersBuilder baseName(@Nullable String parameterName) {
            this.baseParameterName = parameterName;
            return this;
        }

        public ParametersBuilder targetsName(@Nullable String parameterName) {
            this.targetsParameterName = parameterName;
            return this;
        }

        public ParametersBuilder startName(@Nullable String parameterName) {
            this.startParameterName = parameterName;
            return this;
        }

        public ParametersBuilder endName(@Nullable String parameterName) {
            this.endParameterName = parameterName;
            return this;
        }

        public ParametersBuilder dateFormatter(@Nonnull DateTimeFormatter formatter) {
            this.dateFormatter = formatter;
            return this;
        }

        public Map<String, String> build() {
            Map<String, String> parameters = new HashMap<>(4);

            if (baseParameterName != null) {
                request.getBase()
                        .map(Currency::getCode)
                        .ifPresent(currencyCode -> parameters.put(baseParameterName, currencyCode));
            }

            if (targetsParameterName != null) {
                Optional<String> joinedTargetCodes = joinTargetCodes();
                joinedTargetCodes.ifPresent(s -> parameters.put(targetsParameterName, s));
            }

            LocalDateInterval dateInterval = request.getDateInterval();

            if (startParameterName != null) {
                LocalDate start = dateInterval.start();
                parameters.put(startParameterName, dateFormatter.format(start));
            }

            if (endParameterName != null) {
                LocalDate end = dateInterval.end();
                parameters.put(endParameterName, dateFormatter.format(end));
            }

            return parameters;
        }

        private Optional<String> joinTargetCodes() {
            Set<Currency> targets = request.getTargets();

            if (targets.isEmpty()) {
                return Optional.empty();
            }

            String s = targets.stream()
                    .filter(Objects::nonNull) // Just in case
                    .map(Currency::getCode)
                    .collect(joining(","));

            return s.isEmpty()
                    ? Optional.empty()
                    : Optional.of(s);
        }

        // TODO: equals/hashCode
    }

    private ExchangeRateSetRequests() {}

    public static boolean basicPropertiesAreEqual(
            @Nonnull ExchangeRateSetRequest a,
            @Nonnull ExchangeRateSetRequest b) {
        return Objects.equals(a.getBase(), b.getBase())
                && Objects.equals(a.getTargets(), b.getTargets())
                && Objects.equals(a.getDateInterval(), b.getDateInterval());
    }

    public static int hashBasicProperties(@Nonnull ExchangeRateSetRequest request) {
        return Objects.hash(request.getBase(), request.getTargets(), request.getDateInterval());
    }
}