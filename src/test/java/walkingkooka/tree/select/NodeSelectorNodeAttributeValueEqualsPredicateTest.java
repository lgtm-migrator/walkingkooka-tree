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

package walkingkooka.tree.select;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.naming.StringName;
import walkingkooka.tree.TestNode;

import java.util.Collections;

public class NodeSelectorNodeAttributeValueEqualsPredicateTest
        extends NodeSelectorNodeAttributeValuePredicateTestCase<NodeSelectorNodeAttributeValueEqualsPredicate<TestNode, StringName, StringName, Object>> {

    @Test
    public void testEquals() {
        this.testTrue(TestNode.with("node").setAttributes(Collections.singletonMap(ATTRIBUTE_NAME1, VALUE)));
    }

    @Test
    public void testUnequalValue() {
        this.testFalse(TestNode.with("node").setAttributes(Collections.singletonMap(ATTRIBUTE_NAME1, "!!!" + VALUE)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createPredicate(), "@attribute-1=\"123\"");
    }

    @Override
    NodeSelectorNodeAttributeValueEqualsPredicate<TestNode, StringName, StringName, Object> createPredicate(final StringName name, final Object value) {
        return NodeSelectorNodeAttributeValueEqualsPredicate.with(name, value);
    }

    @Override
    public Class<NodeSelectorNodeAttributeValueEqualsPredicate<TestNode, StringName, StringName, Object>> type() {
        return Cast.to(NodeSelectorNodeAttributeValueEqualsPredicate.class);
    }
}
