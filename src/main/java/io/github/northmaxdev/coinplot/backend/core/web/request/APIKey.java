// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;

public record APIKey(
        @Nonnull String name,
        @Nonnull String value,
        @Nonnull SpecificationStrategy specificationStrategy) {

    private static final APIKey NONE = new APIKey("", "", SpecificationStrategy.NONE);

    public enum SpecificationStrategy {
        AS_QUERY_PARAMETER,
        AS_HEADER,
        NONE
    }

    public static APIKey asQueryParameter(@Nonnull String name, @Nonnull String value) {
        return new APIKey(name, value, SpecificationStrategy.AS_QUERY_PARAMETER);
    }

    public static APIKey asHeader(@Nonnull String name, @Nonnull String value) {
        return new APIKey(name, value, SpecificationStrategy.AS_HEADER);
    }

    public static APIKey none() {
        return NONE;
    }

    public boolean isSpecifiedAsQueryParameter() {
        return specificationStrategy == SpecificationStrategy.AS_QUERY_PARAMETER;
    }

    public boolean isSpecifiedAsHeader() {
        return specificationStrategy == SpecificationStrategy.AS_HEADER;
    }

    public boolean isNotSpecified() {
        return specificationStrategy == SpecificationStrategy.NONE;
    }
}
