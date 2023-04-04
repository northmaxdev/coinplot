// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.fn;

@FunctionalInterface
public interface TriConsumer<A, B, C> {

    void accept(A a, B b, C c);
}
