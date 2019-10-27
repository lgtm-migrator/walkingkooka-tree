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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Base class for all arithmetic {@link ExpressionBinaryNode} nodes such as addition, power etc.
 */
abstract class ExpressionArithmeticBinaryNode extends ExpressionBinaryNode2 {

    ExpressionArithmeticBinaryNode(final int index, final ExpressionNode left, final ExpressionNode right) {
        super(index, left, right);
    }

    // evaluation .....................................................................................................

    @Override
    public final Number toValue(final ExpressionEvaluationContext context) {
        return this.toNumber(context);
    }

    @Override
    final ExpressionNode applyText(final String left, final String right, final ExpressionEvaluationContext context) {
        return ExpressionNode.text(this.applyText0(left, right, context));
    }

    /**
     * Currently only addition supports two text parameters, which it concats both, all other operands throw
     * {@link UnsupportedOperationException}
     */
    abstract String applyText0(final String left, final String right, final ExpressionEvaluationContext context);

    final ExpressionNode applyBigDecimal(final BigDecimal left, final BigDecimal right, final ExpressionEvaluationContext context) {
        return ExpressionNode.bigDecimal(this.applyBigDecimal0(left, right, context));
    }

    abstract BigDecimal applyBigDecimal0(final BigDecimal left, final BigDecimal right, final ExpressionEvaluationContext context);

    @Override
    final ExpressionNode applyBigInteger(final BigInteger left, final BigInteger right, final ExpressionEvaluationContext context) {
        return ExpressionNode.bigInteger(this.applyBigInteger0(left, right, context));
    }

    abstract BigInteger applyBigInteger0(final BigInteger left, final BigInteger right, final ExpressionEvaluationContext context);

    @Override
    final ExpressionNode applyDouble(final double left, final double right, final ExpressionEvaluationContext context) {
        return ExpressionNode.doubleNode(Double.isFinite(left) && Double.isFinite(right) ?
                this.applyDouble0(left, right, context) :
                left);
    }

    abstract double applyDouble0(final double left, final double right, final ExpressionEvaluationContext context);

    @Override
    final ExpressionNode applyLong(final long left, final long right, final ExpressionEvaluationContext context) {
        return ExpressionNode.longNode(this.applyLong0(left, right, context));
    }

    abstract long applyLong0(final long left, final long right, final ExpressionEvaluationContext context);
}
