// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.Optional;

@Entity
@SuppressWarnings("NotNullFieldNotInitialized")
public class Currency {

    @Id
    @Column(nullable = false)
    private @Nonnull String code;

    @Column(nullable = false)
    private @Nonnull String name;

    @Column
    private @Nullable String nativeSymbol;

    public Currency(@Nonnull String code, @Nonnull String name, @Nullable String nativeSymbol) {
        this.code = code;
        this.name = name;
        this.nativeSymbol = nativeSymbol;
    }

    public Currency(@Nonnull String code, @Nonnull String name) {
        this(code, name, null);
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

    public Optional<String> getNativeSymbol() {
        return Optional.ofNullable(nativeSymbol);
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
        return name;
    }
}
