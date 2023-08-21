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

@Entity
public class Currency implements Serializable { // Non-final (JPA requirement)

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

    protected Currency() {
        // JPA spec requires a public or protected no-arg constructor
    }

    public @Nonnull String getCode() {
        return code;
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        // Null-safety just in case
        return obj instanceof Currency that
                && Objects.equals(this.code, that.code);
    }

    @Override
    public int hashCode() {
        // Null-safety just in case
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        // AFAIK, accessing via getters gives a better guarantee that the
        // persistence provider will actually initialize the property.
        return getName();
    }
}
