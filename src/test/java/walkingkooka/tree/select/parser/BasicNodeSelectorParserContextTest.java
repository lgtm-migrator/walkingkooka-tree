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

package walkingkooka.tree.select.parser;

import org.junit.jupiter.api.Test;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.expression.ExpressionNumberContexts;
import walkingkooka.tree.expression.ExpressionNumberKind;

import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicNodeSelectorParserContextTest implements ClassTesting2<BasicNodeSelectorParserContext>,
        NodeSelectorParserContextTesting<BasicNodeSelectorParserContext> {

    @Test
    public void testWithNullHasMathContextFails() {
        assertThrows(NullPointerException.class, () -> BasicNodeSelectorParserContext.with(null));
    }

    @Override
    public void testCurrencySymbol() {
    }

    @Override
    public void testGroupingSeparator() {
    }

    @Override
    public void testMathContext() {
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createContext(),
                "decimalSeparator='.' exponentSymbol=\"E\" negativeSign='-' percentageSymbol='%' positiveSign='+'");
    }

    @Override
    public BasicNodeSelectorParserContext createContext() {
        return BasicNodeSelectorParserContext.with(
                ExpressionNumberContexts.basic(
                        ExpressionNumberKind.DEFAULT,
                        this.decimalNumberContext()
                )
        );
    }

    @Override
    public String currencySymbol() {
        return this.decimalNumberContext().currencySymbol();
    }

    @Override
    public char decimalSeparator() {
        return this.decimalNumberContext().decimalSeparator();
    }

    @Override
    public String exponentSymbol() {
        return this.decimalNumberContext().exponentSymbol();
    }

    @Override
    public char groupingSeparator() {
        return this.decimalNumberContext().groupingSeparator();
    }

    @Override
    public MathContext mathContext() {
        return MathContext.DECIMAL32;
    }

    @Override
    public char negativeSign() {
        return this.decimalNumberContext().negativeSign();
    }

    @Override
    public char percentageSymbol() {
        return this.decimalNumberContext().percentageSymbol();
    }

    @Override
    public char positiveSign() {
        return this.decimalNumberContext().positiveSign();
    }

    private DecimalNumberContext decimalNumberContext() {
        return DecimalNumberContexts.american(this.mathContext());
    }

    @Override
    public Class<BasicNodeSelectorParserContext> type() {
        return BasicNodeSelectorParserContext.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
