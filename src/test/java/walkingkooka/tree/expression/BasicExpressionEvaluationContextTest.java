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

package walkingkooka.tree.expression;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionContext;
import walkingkooka.tree.expression.function.ExpressionFunctionContexts;
import walkingkooka.tree.expression.function.FakeExpressionFunction;
import walkingkooka.tree.expression.function.UnknownExpressionFunctionException;

import java.math.MathContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicExpressionEvaluationContextTest implements ClassTesting2<BasicExpressionEvaluationContext>,
        ExpressionEvaluationContextTesting<BasicExpressionEvaluationContext>,
        ToStringTesting<BasicExpressionEvaluationContext> {

    private final static ExpressionReference REFERENCE = new ExpressionReference() {
    };

    private final static Object REFERENCE_VALUE = "expression node 123";
    @Test
    public void testWithNullFunctionsFails() {
        assertThrows(
                NullPointerException.class,
                () -> BasicExpressionEvaluationContext.with(
                        null,
                        this.functionContext()
                )
        );
    }

    @Test
    public void testWithNullFunctionContextFails() {
        assertThrows(
                NullPointerException.class,
                () -> BasicExpressionEvaluationContext.with(
                        this.functions(),
                        null
                )
        );
    }

    @Test
    public void testEvaluateTrue() {
        this.evaluateAndCheck2(true);
    }

    @Test
    public void testEvaluateFalse() {
        this.evaluateAndCheck2(false);
    }

    private void evaluateAndCheck2(final boolean value) {
        this.evaluateAndCheck(
                Expression.value(value),
                value
        );
    }

    @Test
    public void testEvaluateString() {
        final String value = "abc123";
        this.evaluateAndCheck(
                Expression.value(value),
                value
        );
    }

    @Test
    public void testEvaluate() {
        this.checkEquals(this.functionValue(),
                this.createContext()
                        .evaluate(this.functionName(), this.parameters()));
    }

    @Test
    public void testIsPureTrue() {
        this.isPureAndCheck2(true);
    }

    @Test
    public void testIsPureFalse() {
        this.isPureAndCheck2(false);
    }

    private void isPureAndCheck2(final boolean pure) {
        this.isPureAndCheck(
                this.createContext(pure),
                this.functionName(),
                pure
        );
    }

    @Test
    public void testReferences() {
        this.checkEquals(
                Optional.of(REFERENCE_VALUE),
                this.createContext()
                        .reference(REFERENCE)
        );
    }

    @Test
    public void testConvert() {
        this.convertAndCheck(123.0, Long.class, 123L);
    }

    @Test
    public void testToString() {
        final Function<FunctionExpressionName, ExpressionFunction<?, ExpressionFunctionContext>> functions = this.functions();
        final ExpressionFunctionContext functionContext = this.functionContext();

        this.toStringAndCheck(
                BasicExpressionEvaluationContext.with(
                        functions,
                        functionContext
                ),
                functions + " " + functionContext
        );
    }

    @Override
    public BasicExpressionEvaluationContext createContext() {
        return this.createContext(true);
    }

    private BasicExpressionEvaluationContext createContext(final boolean pure) {
        return BasicExpressionEvaluationContext.with(
                this.functions(pure),
                this.functionContext()
        );
    }

    private Function<FunctionExpressionName, ExpressionFunction<?, ExpressionFunctionContext>> functions() {
        return this.functions(true);
    }

    private Function<FunctionExpressionName, ExpressionFunction<?, ExpressionFunctionContext>> functions(final boolean pure) {
        return (functionName) -> {
            Objects.requireNonNull(functionName, "functionName");

            if (false == this.functionName().equals(functionName)) {
                throw new UnknownExpressionFunctionException(functionName);
            }

            return new FakeExpressionFunction<>() {
                @Override
                public Object apply(final List<Object> parameters,
                                    final ExpressionFunctionContext context) {
                    Objects.requireNonNull(parameters, "parameters");
                    Objects.requireNonNull(context, "context");

                    return BasicExpressionEvaluationContextTest.this.functionValue();
                }

                @Override
                public boolean isPure(final ExpressionPurityContext context) {
                    return pure;
                }
            };
        };
    }

    private FunctionExpressionName functionName() {
        return FunctionExpressionName.with("sum");
    }

    private List<Object> parameters() {
        return Lists.of("parameter-1", 2);
    }

    private Object functionValue() {
        return "function-value-234";
    }

    private ExpressionFunctionContext functionContext() {
        return ExpressionFunctionContexts.basic(
                ExpressionNumberKind.DEFAULT,
                this.functions(),
                this.references(),
                ExpressionFunctionContexts.referenceNotFound(),
                this.converterContext()
        );
    }

    private Function<ExpressionReference, Optional<Object>> references() {
        return (r -> {
            Objects.requireNonNull(r, "references");
            this.checkEquals(REFERENCE, r, "reference");
            return Optional.of(REFERENCE_VALUE);
        });
    }

    private ConverterContext converterContext() {
        return ConverterContexts.basic(Converters.numberNumber(),
                DateTimeContexts.fake(),
                DecimalNumberContexts.american(MathContext.DECIMAL32));
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
        return this.decimalNumberContext().mathContext();
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
        return DecimalNumberContexts.american(MathContext.DECIMAL32);
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<BasicExpressionEvaluationContext> type() {
        return Cast.to(BasicExpressionEvaluationContext.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
