// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface ExchangeRateSetRequest extends APIRequest {

    @Nonnull Currency getBase();

    // Must not be empty and must not contain nulls!
    @Nonnull Set<Currency> getTargets();

    @Nonnull LocalDateInterval getDateInterval();

    default @Nonnull Stream<Exchange> toExchanges() {
        return getDateInterval()
                .dates()
                .flatMap(date -> getTargets()
                        .stream()
                        .map(target -> new Exchange(getBase(), target, date)));
    }

    // Constructs a "reduced" version of this request by removing the provided exchanges.
    // Calling this method with a null argument is permitted and is equivalent to an empty
    // set, which in turn is effectively equivalent to removing no exchanges and should
    // return this request as-is. If the reduction operation ends up removing all the
    // exchanges contained in this request, then an empty Optional is returned.
    // If a given exchange is not contained in this request, it is ignored.
    Optional<? extends ExchangeRateSetRequest> reduced(@Nullable Set<Exchange> exchangesToRemove);
}
