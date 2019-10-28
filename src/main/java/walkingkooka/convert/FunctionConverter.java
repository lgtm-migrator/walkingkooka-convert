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

import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link Converter} passes the given value to a {@link Function} such as a method handle to a static method which performs the conversion.
 */
final class FunctionConverter<S, D> extends Converter2 {

    static <S, D> FunctionConverter<S, D> with(final Class<S> sourceType,
                                               final Class<D> targetType,
                                               final Function<S, D> converter) {
        Objects.requireNonNull(sourceType, "sourceType");
        Objects.requireNonNull(targetType, "targetType");

        if (sourceType.equals(targetType)) {
            throw new IllegalArgumentException("Source and target types are the same" + sourceType.getName());
        }

        return new FunctionConverter<>(sourceType, targetType, converter);
    }

    /**
     * Private ctor use static factory.
     */
    private FunctionConverter(final Class<S> sourceType,
                              final Class<D> targetType,
                              final Function<S, D> converter) {
        super();
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.converter = converter;
    }

    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final ConverterContext context) {
        return this.sourceType.isInstance(value) &&
                this.targetType == type;
    }

    @Override
    <T> Either<T, String> convert0(final Object value,
                                   final Class<T> type,
                                   final ConverterContext context) {
        return Either.left(type.cast(this.converter.apply(this.sourceType.cast(value))));
    }

    private final Class<S> sourceType;
    private final Class<D> targetType;
    private final Function<S, D> converter;

    @Override
    public String toString() {
        return this.sourceType.getSimpleName() + "->" + this.targetType.getSimpleName();
    }
}
