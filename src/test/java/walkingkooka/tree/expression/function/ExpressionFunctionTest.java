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

package walkingkooka.tree.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ExpressionFunctionTest implements ClassTesting<ExpressionFunction<?, ?>> {

    private final static ExpressionFunctionParameter<Integer> REQUIRED = ExpressionFunctionParameterName.with("required")
            .required(Integer.class);

    private final static ExpressionFunctionParameter<Boolean> OPTIONAL = ExpressionFunctionParameterName.with("optional")
            .optional(Boolean.class);

    private final static ExpressionFunctionParameter<String> VARIABLE = ExpressionFunctionParameterName.with("variable")
            .variable(String.class);

    // checkParameterCount..............................................................................................

    @Test
    public void testCheckParametersRequired() {
        this.checkParameters(1, REQUIRED);
    }

    @Test
    public void testCheckParametersRequiredRequired() {
        this.checkParameters(2, REQUIRED, REQUIRED);
    }

    @Test
    public void testCheckParametersRequiredRequiredRequired() {
        this.checkParameters(3, REQUIRED, REQUIRED, REQUIRED);
    }

    @Test
    public void testCheckParametersRequiredRequiredExtraFails() {
        this.checkParametersFails(2, REQUIRED);
    }

    @Test
    public void testCheckParametersOptional() {
        this.checkParameters(1, OPTIONAL);
    }

    @Test
    public void testCheckParametersOptionalMissing() {
        this.checkParameters(0, OPTIONAL);
    }

    @Test
    public void testCheckParametersOptionalOptionalMissingMissing() {
        this.checkParameters(0, OPTIONAL, OPTIONAL);
    }

    @Test
    public void testCheckParametersOptionalOptionalPresentMissing() {
        this.checkParameters(1, OPTIONAL, OPTIONAL);
    }

    @Test
    public void testCheckParametersOptionalOptionalPresentPresent() {
        this.checkParameters(2, OPTIONAL, OPTIONAL);
    }

    @Test
    public void testCheckParametersOptionalOptionalPresentPresentExtraFails() {
        this.checkParametersFails(3, OPTIONAL, OPTIONAL);
    }

    @Test
    public void testCheckParametersVariableNone() {
        this.checkParameters(0, VARIABLE);
    }

    @Test
    public void testCheckParametersVariable() {
        this.checkParameters(1, VARIABLE);
    }

    @Test
    public void testCheckParametersVariable2() {
        this.checkParameters(2, VARIABLE);
    }

    @Test
    public void testCheckParametersRequiredVariable0Fails() {
        this.checkParametersFails(0, REQUIRED, VARIABLE);
    }

    @Test
    public void testCheckParametersRequiredVariable1() {
        this.checkParameters(1, REQUIRED, VARIABLE);
    }

    @Test
    public void testCheckParametersRequiredVariable2() {
        this.checkParameters(2, REQUIRED, VARIABLE);
    }

    @Test
    public void testCheckParametersRequiredVariable3() {
        this.checkParameters(3, REQUIRED, VARIABLE);
    }

    @Test
    public void testCheckParametersRequiredOptional0Fails() {
        this.checkParametersFails(0, REQUIRED, OPTIONAL);
    }

    @Test
    public void testCheckParametersRequiredOptional1() {
        this.checkParameters(1, REQUIRED, OPTIONAL);
    }

    @Test
    public void testCheckParametersRequiredOptional2() {
        this.checkParameters(2, REQUIRED, OPTIONAL);
    }

    @Test
    public void testCheckParametersRequiredOptional3Fails() {
        this.checkParametersFails(3, REQUIRED, OPTIONAL);
    }

    private void checkParameters(final int count,
                                 final ExpressionFunctionParameter<?>... parameters) {
        new FakeExpressionFunction<Void, FakeExpressionEvaluationContext>() {
            @Override
            public List<ExpressionFunctionParameter<?>> parameters() {
                return Lists.of(parameters);
            }
        }.checkParameterCount(Collections.nCopies(count, null));
    }


    private void checkParametersFails(final int count,
                                      final ExpressionFunctionParameter<?>... parameters) {
        boolean failed = false;

        try {
            this.checkParameters(count, parameters);
        } catch (final IllegalArgumentException expected) {
            failed = true;
        }
        this.checkEquals(true, failed);
    }

    // parameter....... ...............................................................................................

    @Test
    public void testParameterIndexLessThanZeroFails() {
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> new FakeExpressionFunction<Void, ExpressionEvaluationContext>().parameter(-1)
        );
    }

    @Test
    public void testParameterOutOfBoundsFails() {
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> new FakeExpressionFunction<Void, ExpressionEvaluationContext>() {
                    @Override
                    public List<ExpressionFunctionParameter<?>> parameters() {
                        return Lists.empty();
                    }
                }.parameter(0)
        );
    }

    @Test
    public void testParameterOutOfBoundsFails2() {
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> new FakeExpressionFunction<Void, ExpressionEvaluationContext>() {
                    @Override
                    public List<ExpressionFunctionParameter<?>> parameters() {
                        return Lists.of(REQUIRED);
                    }
                }.parameter(1)
        );
    }

    @Test
    public void testParametersNonVariable() {
        this.parameterAndCheck(
                Lists.of(REQUIRED),
                0,
                REQUIRED
        );
    }

    @Test
    public void testParametersNonVariable2() {
        this.parameterAndCheck(
                Lists.of(REQUIRED, OPTIONAL),
                1,
                OPTIONAL
        );
    }

    @Test
    public void testParametersVariable() {
        this.parameterAndCheck(
                Lists.of(VARIABLE),
                2,
                VARIABLE
        );
    }

    @Test
    public void testParametersVariable2() {
        this.parameterAndCheck(
                Lists.of(REQUIRED, OPTIONAL, VARIABLE),
                2,
                VARIABLE
        );
    }

    @Test
    public void testParametersVariable3() {
        this.parameterAndCheck(
                Lists.of(REQUIRED, OPTIONAL, VARIABLE),
                99,
                VARIABLE
        );
    }

    private void parameterAndCheck(final List<ExpressionFunctionParameter<?>> parameters,
                                   final int index,
                                   final ExpressionFunctionParameter<?> parameter) {
        this.parameterAndCheck(
                new FakeExpressionFunction<Void, ExpressionEvaluationContext>() {
                    @Override
                    public List<ExpressionFunctionParameter<?>> parameters() {
                        return parameters;
                    }
                },
                index,
                parameter
        );
    }

    private void parameterAndCheck(final FakeExpressionFunction<?, ?> function,
                                   final int index,
                                   final ExpressionFunctionParameter<?> parameter) {
        this.checkEquals(
                parameter,
                function.parameter(index),
                "function " + index
        );
    }

    // ClassTesting....................................................................................................

    @Override
    public Class<ExpressionFunction<?, ?>> type() {
        return Cast.to(ExpressionFunction.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
