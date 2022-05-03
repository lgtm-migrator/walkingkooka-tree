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
import walkingkooka.convert.CanConvertTesting;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionNumberContextTesting;
import walkingkooka.tree.expression.FunctionExpressionName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Mixing testing interface for {@link ExpressionFunctionContext}
 */
public interface ExpressionFunctionContextTesting<C extends ExpressionFunctionContext> extends CanConvertTesting<C>,
        ExpressionNumberContextTesting<C>,
        TreePrintableTesting {

    @Test
    default void testFunctionNullFunctionNameFails() {
        assertThrows(
                NullPointerException.class,
                () -> this.createContext()
                        .function(null)
        );
    }

    @Test
    default void testFunctionUnknownFunctionNameFails() {
        assertThrows(
                UnknownExpressionFunctionException.class,
                () -> this.createContext()
                        .function(this.unknownFunctionName())
        );
    }

    @Test
    default void testEvaluateNullFunctionNameFails() {
        assertThrows(
                NullPointerException.class,
                () -> this.createContext()
                        .evaluate(
                                null,
                                ExpressionEvaluationContext.NO_PARAMETERS
                        )
        );
    }

    @Test
    default void testEvaluateUnknownFunctionNameFails() {
        assertThrows(
                UnknownExpressionFunctionException.class,
                () -> this.createContext()
                        .evaluate(
                                this.unknownFunctionName(),
                                ExpressionFunctionContext.NO_PARAMETERS
                        )
        );
    }

    @Test
    default void testEvaluateFunctionNullParametersFails() {
        assertThrows(
                NullPointerException.class,
                () -> this.createContext()
                        .evaluate(
                                FunctionExpressionName.with("sum"),
                                null
                        )
        );
    }

    @Test
    default void testReferenceNullReferenceFails() {
        assertThrows(
                NullPointerException.class,
                () -> this.createContext()
                        .reference(null)
        );
    }

    default void evaluateAndCheck(final FunctionExpressionName name,
                                  final List<Object> parameters,
                                  final Object expected) {

        this.evaluateAndCheck(
                this.createContext(),
                name,
                parameters,
                expected
        );
    }

    default void evaluateAndCheck(final C context,
                                  final FunctionExpressionName name,
                                  final List<Object> parameters,
                                  final Object expected) {

        this.checkEquals(
                expected,
                context.evaluate(name, parameters),
                () -> "evaluate " + name + " " + parameters
        );
    }

    @Override
    default String typeNameSuffix() {
        return ExpressionFunctionContext.class.getSimpleName();
    }

    default C createCanConvert() {
        return this.createContext();
    }

    default FunctionExpressionName unknownFunctionName() {
        return FunctionExpressionName.with("unknown-function-123");
    }
}
