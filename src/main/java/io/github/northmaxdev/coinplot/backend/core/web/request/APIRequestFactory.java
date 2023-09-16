// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;

@FunctionalInterface
public interface APIRequestFactory<R extends APIRequest> {

    @Nonnull R create() throws CannotCreateAPIRequestException;
}
