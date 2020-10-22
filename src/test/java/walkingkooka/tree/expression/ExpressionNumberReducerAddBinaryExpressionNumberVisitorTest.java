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

public final class ExpressionNumberReducerAddBinaryExpressionNumberVisitorTest extends ExpressionNumberReducerBinaryExpressionNumberVisitorTestCase2<ExpressionNumberReducerAddBinaryExpressionNumberVisitor> {

    @Override
    public ExpressionNumberReducerAddBinaryExpressionNumberVisitor createVisitor() {
        return new ExpressionNumberReducerAddBinaryExpressionNumberVisitor(CONTEXT);
    }

    @Override
    int left() {
        return 1;
    }

    @Override
    int right() {
        return 2;
    }

    @Override
    int expected() {
        return 3;
    }

    @Override
    Number function(final Number left, final Number right, final ExpressionNumberReducerContext context) {
        return ExpressionNumberReducerAddBinaryExpressionNumberVisitor.compute(left, right, context);
    }

    @Override
    String expectedToString() {
        return "add";
    }

    @Override
    public Class<ExpressionNumberReducerAddBinaryExpressionNumberVisitor> type() {
        return ExpressionNumberReducerAddBinaryExpressionNumberVisitor.class;
    }
}
