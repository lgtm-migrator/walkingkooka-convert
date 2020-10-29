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

import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.Value;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserToken;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A {@link Converter} that only accepts {@link String strings} and attempts to parse them and return the result from a {@link ParserToken} that has a {@link Value}.
 * If the parser fails then a {@link #failConversion(Object, Class)} happens.
 */
final class ParserConverter<V, P extends ParserContext, C extends ConverterContext> implements Converter<C> {

    static <V, P extends ParserContext, C extends ConverterContext> ParserConverter<V, P, C> with(final Class<V> type,
                                                                                                  final Parser<P> parser,
                                                                                                  final Function<C, P> context) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(parser, "parser");
        Objects.requireNonNull(context, "context");

        return new ParserConverter<>(type, parser, context);
    }

    /**
     * Private ctor use factory.
     */
    private ParserConverter(final Class<V> type,
                            final Parser<P> parser,
                            final Function<C, P> context) {
        this.type = type;
        this.parser = parser;
        this.context = context;
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return value instanceof String && this.type == type;
    }

    private final Class<V> type;

    @Override
    public <T> Either<T, String> convert(final Object value,
                                         final Class<T> type,
                                         final C context) {
        return value instanceof String ?
                parseString((String) value, type, context) :
                this.failConversion(value, type);
    }

    private <T> Either<T, String> parseString(final String text,
                                              final Class<T> type,
                                              final C context) {
        final TextCursor cursor = TextCursors.charSequence(text);
        final Optional<ParserToken> result = this.parser.parse(cursor, this.context.apply(context));
        return result.isPresent() ?
                Either.left(Cast.to(((Value) result.get()).value())) :
                this.failConversion(text, type);
    }

    private final Parser<P> parser;
    private final Function<C, P> context;

    @Override
    public String toString() {
        return "String->" + this.type.getSimpleName();
    }
}
