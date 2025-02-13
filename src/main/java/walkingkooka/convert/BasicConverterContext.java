/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.convert;

import walkingkooka.Either;
import walkingkooka.datetime.DateTimeContext;
import walkingkooka.math.DecimalNumberContext;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * An adaptor for {@link DecimalNumberContext} to {@link ConverterContext}.
 */
final class BasicConverterContext implements ConverterContext {

    /**
     * Creates a new {@link BasicConverterContext}.
     */
    static BasicConverterContext with(final Converter<ConverterContext> converter,
                                      final DateTimeContext dateTimeContext,
                                      final DecimalNumberContext decimalNumberContext) {
        Objects.requireNonNull(converter, "converter");
        Objects.requireNonNull(dateTimeContext, "dateTimeContext");
        Objects.requireNonNull(decimalNumberContext, "decimalNumberContext");

        return new BasicConverterContext(converter,
                dateTimeContext,
                decimalNumberContext);
    }

    /**
     * Private ctor use factory
     */
    private BasicConverterContext(final Converter<ConverterContext> converter,
                                  final DateTimeContext dateTimeContext,
                                  final DecimalNumberContext decimalNumberContext) {
        super();

        this.converter = converter;
        this.dateTimeContext = dateTimeContext;
        this.decimalNumberContext = decimalNumberContext;
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type) {
        return this.converter.canConvert(value, type, this);
    }

    @Override
    public <T> Either<T, String> convert(final Object value,
                                         final Class<T> target) {
        return this.converter.convert(value, target, this);
    }

    private final Converter<ConverterContext> converter;

    @Override
    public List<String> ampms() {
        return this.dateTimeContext.ampms();
    }

    @Override
    public int defaultYear() {
        return this.dateTimeContext.defaultYear();
    }

    @Override
    public List<String> monthNames() {
        return this.dateTimeContext.monthNames();
    }

    @Override
    public List<String> monthNameAbbreviations() {
        return this.dateTimeContext.monthNameAbbreviations();
    }

    @Override
    public LocalDateTime now() {
        return this.dateTimeContext.now();
    }

    @Override
    public int twoDigitYear() {
        return this.dateTimeContext.twoDigitYear();
    }

    @Override
    public List<String> weekDayNames() {
        return this.dateTimeContext.weekDayNames();
    }

    @Override
    public List<String> weekDayNameAbbreviations() {
        return this.dateTimeContext.weekDayNameAbbreviations();
    }

    private final DateTimeContext dateTimeContext;

    // DecimalNumberContext.............................................................................................

    @Override
    public String currencySymbol() {
        return this.decimalNumberContext.currencySymbol();
    }

    @Override
    public char decimalSeparator() {
        return this.decimalNumberContext.decimalSeparator();
    }

    @Override
    public String exponentSymbol() {
        return this.decimalNumberContext.exponentSymbol();
    }

    @Override
    public char groupingSeparator() {
        return this.decimalNumberContext.groupingSeparator();
    }

    @Override
    public char negativeSign() {
        return this.decimalNumberContext.negativeSign();
    }

    @Override
    public char percentageSymbol() {
        return this.decimalNumberContext.percentageSymbol();
    }

    @Override
    public char positiveSign() {
        return this.decimalNumberContext.positiveSign();
    }

    @Override
    public Locale locale() {
        return this.decimalNumberContext.locale();
    }

    @Override
    public MathContext mathContext() {
        return this.decimalNumberContext.mathContext();
    }

    private final DecimalNumberContext decimalNumberContext;

    @Override
    public String toString() {
        return this.dateTimeContext + " " + this.decimalNumberContext;
    }
}
