// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

@Entity
public class Currency implements Serializable { // Required by the JPA spec to be non-final (JPA 3.1, section 2.1)

    @Serial
    private static final long serialVersionUID = -1322860514883265661L;

    @Id
    @Column(nullable = false)
    private @Nonnull String code;

    @Column(nullable = false)
    private @Nonnull String name;

    public Currency(@Nonnull String code, @Nonnull String name) {
        this.code = Objects.requireNonNull(code, "Currency code cannot be null");
        this.name = Objects.requireNonNull(name, "Currency name cannot be null");
    }

    public static @Nonnull Currency ofMapEntry(@Nonnull Map.Entry<String, String> mapEntry) {
        Objects.requireNonNull(mapEntry);
        return new Currency(mapEntry.getKey(), mapEntry.getValue());
    }

    public static @Nonnull Set<Currency> createSet(@Nonnull Map<String, String> map) {
        return Objects.requireNonNull(map)
                .entrySet()
                .stream()
                .map(Currency::ofMapEntry)
                .collect(toUnmodifiableSet());
    }

    protected Currency() {
        // JPA spec requires a public or protected no-arg constructor (JPA 3.1, section 2.1)
    }

    public @Nonnull String getCode() {
        return code;
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Currency that
                && Objects.equals(this.code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return code;
    }
}
