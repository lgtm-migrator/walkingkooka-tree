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

package walkingkooka.tree.expression;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.tree.expression.function.ExpressionFunctionKind;
import walkingkooka.tree.expression.function.FakeExpressionFunction;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class ExpressionEvaluationContextPrepareParametersListTest extends ExpressionEvaluationContextPrepareParametersListTestCase<ExpressionEvaluationContextPrepareParametersList> {

    @Test
    public void testEmptyKinds() {
        final List<Object> parameters = Lists.of(1, 2, 3);

        assertSame(
                parameters,
                ExpressionEvaluationContextPrepareParametersList.with(
                        parameters,
                        new FakeExpressionFunction<>() {
                            @Override
                            public Set<ExpressionFunctionKind> kinds() {
                                return Sets.empty();
                            }
                        },
                        ExpressionEvaluationContexts.fake())
        );
    }

    @Override
    public Class<ExpressionEvaluationContextPrepareParametersList> type() {
        return ExpressionEvaluationContextPrepareParametersList.class;
    }
}