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

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.BigInteger;

final class ExpressionNumberReducerBinaryExpressionNumberVisitorPower extends ExpressionNumberReducerBinaryExpressionNumberVisitor {

    static Number compute(final Number left,
                          final Number right,
                          final ExpressionNumberReducerContext context) {
        final ExpressionNumberReducerBinaryExpressionNumberVisitorPower visitor = new ExpressionNumberReducerBinaryExpressionNumberVisitorPower(context);
        visitor.accept(left, right);
        return visitor.result;
    }

    ExpressionNumberReducerBinaryExpressionNumberVisitorPower(final ExpressionNumberReducerContext context) {
        super(context);
    }

    @Override
    protected void visit(final BigDecimal left, final BigDecimal right) {
        this.result = BigDecimalMath.pow(left, right, this.context.mathContext());
    }

    @Override
    protected void visit(final BigInteger left, final BigInteger right) {
        this.accept(this.toBigDecimal(left), this.toBigDecimal(right));
        this.result = this.toBigDecimal(this.result).toBigIntegerExact();
    }

    @Override
    protected void visit(final Double left, final Double right) {
        this.result = Math.pow(left, right);
    }

    @Override
    protected void visit(final Long left, final Long right) {
        this.result = Math.pow(left, right); // TODO handle overflow https://github.com/mP1/walkingkooka-tree/issues/63
    }

    @Override
    public String toString() {
        return "power";
    }
}
