/*
 * Copyright 2020 Miroslav Pokorny (github.com/mP1)
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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.FunctionExpressionName;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * A {@link ExpressionFunction} that supports binding given parameter values using the parameter names to a {@link ExpressionEvaluationContext} before invoking a {@link Function}.
 */
final class LambdaExpressionFunction<T, C extends ExpressionEvaluationContext> implements ExpressionFunction<T, C> {

    static <T, C extends ExpressionEvaluationContext> LambdaExpressionFunction<T, C> with(final boolean pure,
                                                                                          final List<ExpressionFunctionParameter<?>> parameters,
                                                                                          final Class<T> returnType,
                                                                                          final Function<C, T> function,
                                                                                          final BiPredicate<ExpressionFunctionParameterName, ExpressionReference> parameterMatcher) {
        Objects.requireNonNull(parameters, "parameters");
        Objects.requireNonNull(returnType, "returnType");
        Objects.requireNonNull(function, "function");
        Objects.requireNonNull(parameterMatcher, "parameterMatcher");

        return new LambdaExpressionFunction<>(
                pure,
                Lists.immutable(parameters),
                returnType,
                function,
                parameterMatcher
        );
    }

    public LambdaExpressionFunction(final boolean pure,
                                    final List<ExpressionFunctionParameter<?>> parameters,
                                    final Class<T> returnType,
                                    final Function<C, T> function,
                                    final BiPredicate<ExpressionFunctionParameterName, ExpressionReference> parameterMatcher) {
        this.pure = pure;
        this.parameters = parameters;
        this.returnType = returnType;
        this.function = function;
        this.parameterMatcher = parameterMatcher;
    }

    @Override
    public Optional<FunctionExpressionName> name() {
        return ANONYMOUS_NAME;
    }

    @Override
    public boolean isPure(final ExpressionPurityContext context) {
        return this.pure;
    }

    private final boolean pure;

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return this.parameters;
    }

    /**
     * The parameter definitions which are transformed into scoped local variables when the {@link #function} is executed.
     */
    private final List<ExpressionFunctionParameter<?>> parameters;

    @Override
    public Class<T> returnType() {
        return this.returnType;
    }

    private final Class<T> returnType;

    @Override
    public T apply(final List<Object> values,
                   final C context) {
        this.checkParameterCount(values);

        return this.function.apply(
                Cast.to(
                        context.context(
                                LambdaExpressionFunctionExpressionEvaluationContextContextFunction.with(
                                        this.parameters,
                                        values,
                                        this.parameterMatcher
                                )
                        )
                )
        );
    }

    /**
     * Used to match an {@link ExpressionReference} to an {@link ExpressionFunctionParameterName}, like testing
     * a label against a parameter-name.
     */
    private final BiPredicate<ExpressionFunctionParameterName, ExpressionReference> parameterMatcher;

    /**
     * The {@link Function} that will be executed.
     */
    private final Function<C, T> function;

    @Override
    public String toString() {
        return ANONYMOUS;
    }
}
