// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.frontend.util.ValueProviders;
import io.github.northmaxdev.coinplot.lang.math.NumericChange;
import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public final class ExchangeRateMetadataReport
        extends Grid<ExchangeRateBatch>
        implements LocaleChangeObserver {

    private static final String EXCHANGE_COLUMN_HEADER_KEY;
    private static final String LATEST_VALUE_COLUMN_HEADER_KEY;
    private static final String LATEST_CHANGE_DIFFERENCE_COLUMN_HEADER_KEY;
    private static final String LATEST_CHANGE_PERCENTAGE_COLUMN_HEADER_KEY;
    private static final String MIN_VALUE_COLUMN_HEADER_KEY;
    private static final String MAX_VALUE_COLUMN_HEADER_KEY;
    private static final String BATCH_SIZE_COLUMN_HEADER_KEY;

    private static final ValueProvider<ExchangeRateBatch, BigDecimal> LATEST_VALUE_PROVIDER;
    private static final ValueProvider<ExchangeRateBatch, BigDecimal> LATEST_CHANGE_DIFFERENCE_PROVIDER;
    private static final ValueProvider<ExchangeRateBatch, Percentage> LATEST_CHANGE_PERCENTAGE_PROVIDER;
    private static final ValueProvider<ExchangeRateBatch, BigDecimal> MIN_VALUE_PROVIDER;
    private static final ValueProvider<ExchangeRateBatch, BigDecimal> MAX_VALUE_PROVIDER;

    static {
        EXCHANGE_COLUMN_HEADER_KEY = "exchange-rate-metadata-report.exchange-column.header";
        LATEST_VALUE_COLUMN_HEADER_KEY = "exchange-rate-metadata-report.latest-value-column.header";
        LATEST_CHANGE_DIFFERENCE_COLUMN_HEADER_KEY = "exchange-rate-metadata-report.latest-change-difference-column.header";
        LATEST_CHANGE_PERCENTAGE_COLUMN_HEADER_KEY = "exchange-rate-metadata-report.latest-change-percentage-column.header";
        MIN_VALUE_COLUMN_HEADER_KEY = "exchange-rate-metadata-report.min-value-column.header";
        MAX_VALUE_COLUMN_HEADER_KEY = "exchange-rate-metadata-report.max-value-column.header";
        BATCH_SIZE_COLUMN_HEADER_KEY = "exchange-rate-metadata-report.batch-size-column.header";

        LATEST_VALUE_PROVIDER = ValueProviders.unpackingOptionalToNull(ExchangeRateBatch::getLatestValue);
        LATEST_CHANGE_DIFFERENCE_PROVIDER = ValueProviders.unpackingOptionalToNull(batch -> batch.getLatestChange()
                .map(NumericChange::getDifference));
        LATEST_CHANGE_PERCENTAGE_PROVIDER = ValueProviders.unpackingOptionalToNull(batch -> batch.getLatestChange()
                .filter(NumericChange::isPercentageCalculable)
                .map(NumericChange::getPercentage));
        MIN_VALUE_PROVIDER = ValueProviders.unpackingOptionalToNull(ExchangeRateBatch::getMinValue);
        MAX_VALUE_PROVIDER = ValueProviders.unpackingOptionalToNull(ExchangeRateBatch::getMaxValue);
    }

    private final Column<ExchangeRateBatch> exchangeColumn;
    private final Column<ExchangeRateBatch> latestValueColumn;
    private final Column<ExchangeRateBatch> latestChangeDifferenceColumn;
    private final Column<ExchangeRateBatch> latestChangePercentageColumn;
    private final Column<ExchangeRateBatch> minValueColumn;
    private final Column<ExchangeRateBatch> maxValueColumn;
    private final Column<ExchangeRateBatch> batchSizeColumn;

    public ExchangeRateMetadataReport() {
        exchangeColumn = addColumn(ExchangeRateBatch::getExchange);
        latestValueColumn = addColumn(LATEST_VALUE_PROVIDER);
        latestChangeDifferenceColumn = addColumn(LATEST_CHANGE_DIFFERENCE_PROVIDER);
        latestChangePercentageColumn = addColumn(LATEST_CHANGE_PERCENTAGE_PROVIDER);
        minValueColumn = addColumn(MIN_VALUE_PROVIDER);
        maxValueColumn = addColumn(MAX_VALUE_PROVIDER);
        batchSizeColumn = addColumn(ExchangeRateBatch::size);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setGridColumnHeader(exchangeColumn, event, EXCHANGE_COLUMN_HEADER_KEY);
        I18NUtilities.setGridColumnHeader(latestValueColumn, event, LATEST_VALUE_COLUMN_HEADER_KEY);
        I18NUtilities.setGridColumnHeader(latestChangeDifferenceColumn, event, LATEST_CHANGE_DIFFERENCE_COLUMN_HEADER_KEY);
        I18NUtilities.setGridColumnHeader(latestChangePercentageColumn, event, LATEST_CHANGE_PERCENTAGE_COLUMN_HEADER_KEY);
        I18NUtilities.setGridColumnHeader(minValueColumn, event, MIN_VALUE_COLUMN_HEADER_KEY);
        I18NUtilities.setGridColumnHeader(maxValueColumn, event, MAX_VALUE_COLUMN_HEADER_KEY);
        I18NUtilities.setGridColumnHeader(batchSizeColumn, event, BATCH_SIZE_COLUMN_HEADER_KEY);
    }
}
