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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.Parsers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ParserConverterTest extends ConverterTestCase2<ParserConverter<BigDecimal, ParserContext>> {

    @Test
    public void testWithNullTypeFails() {
        assertThrows(NullPointerException.class, () -> ParserConverter.with(null, this.bigDecimalParser(), this.parserContextAdapter()));
    }

    @Test
    public void testWithNullParserFails() {
        assertThrows(NullPointerException.class, () -> ParserConverter.with(BigDecimal.class, null, this.parserContextAdapter()));
    }

    @Test
    public void testWithNullParserContextAdapterFails() {
        assertThrows(NullPointerException.class, () -> ParserConverter.with(BigDecimal.class, this.bigDecimalParser(), null));
    }

    @Test
    public void testParserConverts() {
        this.convertAndCheck("1.23", BigDecimal.class, BigDecimal.valueOf(1.23));
    }

    @Test
    public void testParserNonStringFails() {
        this.convertFails(Integer.MAX_VALUE, BigDecimal.class);
    }

    @Test
    public void testParserFails() {
        this.convertFails("FAILS", BigDecimal.class);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createConverter(), "String->BigDecimal");
    }

    @Override
    public ParserConverter<BigDecimal, ParserContext> createConverter() {
        return ParserConverter.with(BigDecimal.class,
                this.bigDecimalParser(),
                parserContextAdapter());
    }

    private Function<ConverterContext, ParserContext> parserContextAdapter() {
        return (c) -> ParserContexts.basic(c, c);
    }

    @Override
    public ConverterContext createContext() {
        return ConverterContexts.basic(DateTimeContexts.fake(), DecimalNumberContexts.american(MathContext.DECIMAL32));
    }

    private Parser<ParserContext> bigDecimalParser() {
        return Parsers.bigDecimal();
    }

    @Override
    public Class<ParserConverter<BigDecimal, ParserContext>> type() {
        return Cast.to(ParserConverter.class);
    }
}
