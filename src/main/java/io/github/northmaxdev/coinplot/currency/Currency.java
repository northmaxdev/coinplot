// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Currency { // TODO: Explicitly specify access type

    @Id
    @Column(length = 3, nullable = false)
    private @Nonnull String threeLetterISOCode;

    @Column(length = 30, nullable = false)
    private @Nonnull String name;

    public Currency(@Nonnull String threeLetterISOCode, @Nonnull String name) {
        this.threeLetterISOCode = threeLetterISOCode;
        this.name = name;
    }

    protected Currency() {
        // JPA spec requirement
    }

    public @Nonnull String getThreeLetterISOCode() {
        return threeLetterISOCode;
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Currency other
                && Objects.equals(this.threeLetterISOCode, other.threeLetterISOCode);
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
