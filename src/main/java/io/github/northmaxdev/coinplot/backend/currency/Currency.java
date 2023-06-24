// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Currency {

    @Id
    @Column(nullable = false)
    private @Nonnull String threeLetterISOCode;

    @Column(nullable = false)
    private @Nonnull String name;

    public Currency(@Nonnull String threeLetterISOCode, @Nonnull String name) {
        this.threeLetterISOCode = threeLetterISOCode;
        this.name = name;
    }

    protected Currency() {
        // JPA spec requires a public or protected no-arg constructor
    }

    public @Nonnull String getThreeLetterISOCode() {
        return threeLetterISOCode;
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Currency that
               && Objects.equals(this.threeLetterISOCode, that.threeLetterISOCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(threeLetterISOCode);
    }

    @Override
    public String toString() {
        return name;
    }
}
