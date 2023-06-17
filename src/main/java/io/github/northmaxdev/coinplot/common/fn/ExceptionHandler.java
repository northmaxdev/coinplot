// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.common.fn;

@FunctionalInterface
public interface ExceptionHandler {

    void handle(Exception e);
}
