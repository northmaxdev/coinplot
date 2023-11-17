// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.lang.Pair;
import io.github.northmaxdev.coinplot.lang.math.NumericChange;
import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class ExchangeRateMetricsReport extends FormLayout implements LocaleChangeObserver {

    private static final String LATEST_VALUE_FIELD_LABEL_KEY = "exchange-rate-metrics-report.latest-value-field.label";
    private static final String LATEST_CHANGE_DIFFERENCE_FIELD_LABEL_KEY = "exchange-rate-metrics-report.latest-change-difference-field.label";
    private static final String LATEST_CHANGE_PERCENTAGE_FIELD_LABEL_KEY = "exchange-rate-metrics-report.latest-change-percentage-field.label";
    private static final String MIN_VALUE_FIELD_LABEL_KEY = "exchange-rate-metrics-report.min-value-field.label";
    private static final String MAX_VALUE_FIELD_LABEL_KEY = "exchange-rate-metrics-report.max-value-field.label";
    private static final String BATCH_SIZE_FIELD_LABEL_KEY = "exchange-rate-metrics-report.batch-size-field.label";

    private static final List<ResponsiveStep> RESPONSIVE_STEPS = List.of(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("300px", 2),
            new ResponsiveStep("600px", 3)
    );

    private final BigDecimalField latestValueField;
    private final BigDecimalField latestChangeDifferenceField;
    private final NumberField latestChangePercentageField;
    private final BigDecimalField minValueField;
    private final BigDecimalField maxValueField;
    private final IntegerField batchSizeField;

    public ExchangeRateMetricsReport() {
        latestValueField = new BigDecimalField();
        latestValueField.setReadOnly(true);
        latestValueField.setPrefixComponent(VaadinIcon.TIME_FORWARD.create());
        add(latestValueField);

        latestChangeDifferenceField = new BigDecimalField();
        latestChangeDifferenceField.setReadOnly(true);
        latestChangeDifferenceField.setPrefixComponent(VaadinIcon.PLUS_MINUS.create());
        add(latestChangeDifferenceField);

        latestChangePercentageField = new NumberField();
        latestChangePercentageField.setReadOnly(true);
        latestChangePercentageField.setPrefixComponent(VaadinIcon.BOOK_PERCENT.create());
        add(latestChangePercentageField);

        minValueField = new BigDecimalField();
        minValueField.setReadOnly(true);
        minValueField.setPrefixComponent(VaadinIcon.ARROW_DOWN.create());
        add(minValueField);

        maxValueField = new BigDecimalField();
        maxValueField.setReadOnly(true);
        maxValueField.setPrefixComponent(VaadinIcon.ARROW_UP.create());
        add(maxValueField);

        batchSizeField = new IntegerField();
        batchSizeField.setReadOnly(true);
        batchSizeField.setPrefixComponent(VaadinIcon.COIN_PILES.create());
        add(batchSizeField);

        setResponsiveSteps(RESPONSIVE_STEPS);
    }

    public void load(@Nonnull ExchangeRateBatch batch) {
        Objects.requireNonNull(batch);

        Optional<NumericChange<BigDecimal>> latestChange = batch.getLatestChange();
        latestChange.ifPresentOrElse(this::loadLatestChange, this::clearLatestChangeFields);

        Optional<Pair<BigDecimal, BigDecimal>> extremes = batch.getValueExtremes();
        extremes.ifPresentOrElse(pair -> {
            // Remember to not get confused by which pair element is min and which is max
            minValueField.setValue(pair.first());
            maxValueField.setValue(pair.second());
        }, () -> {
            minValueField.clear();
            maxValueField.clear();
        });

        batchSizeField.setValue(batch.size());
    }

    public void clear() {
        clearLatestChangeFields();
        minValueField.clear();
        maxValueField.clear();
        batchSizeField.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        Locale locale = event.getLocale();

        latestValueField.setLocale(locale);
        I18NUtilities.setLabel(latestValueField, locale, LATEST_VALUE_FIELD_LABEL_KEY);

        latestChangeDifferenceField.setLocale(locale);
        I18NUtilities.setLabel(latestChangeDifferenceField, locale, LATEST_CHANGE_DIFFERENCE_FIELD_LABEL_KEY);

        I18NUtilities.setLabel(latestChangePercentageField, locale, LATEST_CHANGE_PERCENTAGE_FIELD_LABEL_KEY);

        minValueField.setLocale(locale);
        I18NUtilities.setLabel(minValueField, locale, MIN_VALUE_FIELD_LABEL_KEY);

        maxValueField.setLocale(locale);
        I18NUtilities.setLabel(maxValueField, locale, MAX_VALUE_FIELD_LABEL_KEY);

        I18NUtilities.setLabel(batchSizeField, locale, BATCH_SIZE_FIELD_LABEL_KEY);
    }

    ////////////////////
    // Helper methods //
    ////////////////////

    private void loadLatestChange(@Nonnull NumericChange<BigDecimal> change) {
        // Null-check is not needed

        latestValueField.setValue(change.getFinalValue());
        latestChangeDifferenceField.setValue(change.getDifference());

        // It is worth considering implementing some kind of visual feedback
        // when the percentage is incalculable.
        if (change.isPercentageCalculable()) {
            Percentage percentage = change.getPercentage();
            latestChangePercentageField.setValue(percentage.value());
        }
    }

    private void clearLatestChangeFields() {
        latestValueField.clear();
        latestChangeDifferenceField.clear();
        latestChangePercentageField.clear();
    }
}
