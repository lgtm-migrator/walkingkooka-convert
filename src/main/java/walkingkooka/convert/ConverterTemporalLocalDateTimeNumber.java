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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Converts a {@link LocalDateTime} into a {@link Number}.
 */
final class ConverterTemporalLocalDateTimeNumber extends ConverterTemporalLocalDateTime<Number> {

    /**
     * Creates a new instance with the given date offset.
     * A value of zero = 1/1/1970.
     */
    static ConverterTemporalLocalDateTimeNumber with(final long offset) {
        return new ConverterTemporalLocalDateTimeNumber(offset);
    }

    /**
     * Private ctor use factory
     */
    private ConverterTemporalLocalDateTimeNumber(final long offset) {
        super(offset);
    }

    @Override
    boolean isTargetType(final Class<?> type) {
        return BigDecimal.class == type ||
                BigInteger.class == type ||
                Byte.class == type ||
                Double.class == type ||
                Float.class == type ||
                Integer.class == type ||
                Long.class == type ||
                Number.class == type ||
                Short.class == type;
    }

    @Override
    <T> Either<T, String> convertFromLocalDateTime(final long days,
                                                   final double time,
                                                   final LocalDateTime dateTime,
                                                   final Class<T> type,
                                                   final ConverterContext context) {
        return this.convertToNumber(days + time,
                type,
                context,
                dateTime);
    }

    @Override
    Class<Number> targetType() {
        return Number.class;
    }
}