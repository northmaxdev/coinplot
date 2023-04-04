// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.fn;

@FunctionalInterface
public interface ExceptionHandler {

    void handle(Exception e);
}
