// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.common.fn;

@FunctionalInterface
public interface TriConsumer<A, B, C> {

    void accept(A a, B b, C c);
}
