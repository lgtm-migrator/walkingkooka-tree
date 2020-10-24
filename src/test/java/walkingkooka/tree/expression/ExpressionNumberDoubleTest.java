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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ExpressionNumberDoubleTest extends ExpressionNumberTestCase<ExpressionNumberDouble> {

    @Test
    public void testWithDoubleNullContextFails() {
        assertThrows(NullPointerException.class, () -> ExpressionNumberDouble.withDouble(1, null));
    }

    // comparable......................................................................................................

    @Test
    public void testCompareBigDecimalEquals() {
        this.compareToAndCheckEquals(ExpressionNumberDouble.withDouble(1, CONTEXT),
                ExpressionNumberBigDecimal.withBigDecimal(BigDecimal.ONE, CONTEXT));
    }

    @Test
    public void testCompareDoubleEquals() {
        this.compareToAndCheckEquals(ExpressionNumberDouble.withDouble(1, CONTEXT),
                ExpressionNumberDouble.withDouble(1, CONTEXT));
    }

    @Test
    public void testCompareBigDecimalLess() {
        this.compareToAndCheckLess(ExpressionNumberDouble.withDouble(1, CONTEXT),
                ExpressionNumberBigDecimal.withBigDecimal(BigDecimal.valueOf(2), CONTEXT));
    }

    @Test
    public void testCompareDoubleLess() {
        this.compareToAndCheckLess(ExpressionNumberDouble.withDouble(1, CONTEXT),
                ExpressionNumberDouble.withDouble(2, CONTEXT));
    }

    @Override
    ExpressionNumberDouble create(final double value) {
        return ExpressionNumberDouble.withDouble(value, CONTEXT);
    }

    @Override
    final void checkValue0(final double value, final ExpressionNumberDouble number) {
        assertEquals(value, number.doubleValue());
    }

    @Override
    public Class<ExpressionNumberDouble> type() {
        return ExpressionNumberDouble.class;
    }
}
