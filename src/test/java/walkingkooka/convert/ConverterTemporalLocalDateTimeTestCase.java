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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;

public abstract class ConverterTemporalLocalDateTimeTestCase<C extends ConverterTemporalLocalDateTime<D>, D> extends ConverterTemporalTestCase<C, LocalDateTime, D> {

    final static int VALUE = 123;
    final static LocalDate DAY = LocalDate.ofEpochDay(VALUE);
    final static LocalTime MIDNIGHT = LocalTime.ofSecondOfDay(0);
    final static LocalTime QUARTER_DAY = LocalTime.of(6, 0);

    ConverterTemporalLocalDateTimeTestCase() {
        super();
    }

    // TypeNameTesting..................................................................................................

    @Override
    public final String typeNamePrefix() {
        return Converter.class.getSimpleName() + Temporal.class.getSimpleName() + LocalDateTime.class.getSimpleName();
    }
}
