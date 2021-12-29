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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;

import java.util.List;
import java.util.stream.Collectors;

public final class ExpressionFunctionTestingTest implements ClassTesting<ExpressionFunctionTesting<?, ?, ?>> {

    // applyAndCheck....................................................................................................

    @Test
    public void testApplyAndCheck2ObjectVar() {
        new ExpressionFunctionTesting<>() {

            @Override
            public Class<ExpressionFunction<Object, ExpressionFunctionContext>> type() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ExpressionFunction<Object, ExpressionFunctionContext> createBiFunction() {
                return stringConcatParameters();
            }

            @Override
            public ExpressionFunctionContext createContext() {
                return ExpressionFunctionTestingTest.this.createContext();
            }
        }.apply2(Lists.of("hello", "2"));
    }

    @Test
    public void testApplyAndCheck2ListResult() {
        new ExpressionFunctionTesting<>() {

            @Override
            public Class<ExpressionFunction<Object, ExpressionFunctionContext>> type() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ExpressionFunction<Object, ExpressionFunctionContext> createBiFunction() {
                return stringConcatParameters();
            }

            @Override
            public ExpressionFunctionContext createContext() {
                return ExpressionFunctionTestingTest.this.createContext();
            }
        }.applyAndCheck2(
                Lists.of("hello", "2"),
                "hello2"
        );
    }

    @Test
    public void testApplyAndCheck2ListResultFails() {
        boolean fails = false;

        try {
            new ExpressionFunctionTesting<>() {

                @Override
                public Class<ExpressionFunction<Object, ExpressionFunctionContext>> type() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public ExpressionFunction<Object, ExpressionFunctionContext> createBiFunction() {
                    return stringConcatParameters();
                }

                @Override
                public ExpressionFunctionContext createContext() {
                    return ExpressionFunctionTestingTest.this.createContext();
                }
            }.applyAndCheck2(
                    Lists.of("hello", "2"),
                    "fail!!"
            );
        } catch (final Error cause) {
            fails = true;
        }
        this.checkEquals(true, fails);
    }

    @Test
    public void testApplyAndCheck2FunctionListResult() {
        new ExpressionFunctionTesting<>() {

            @Override
            public Class<ExpressionFunction<Object, ExpressionFunctionContext>> type() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ExpressionFunction<Object, ExpressionFunctionContext> createBiFunction() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ExpressionFunctionContext createContext() {
                return ExpressionFunctionTestingTest.this.createContext();
            }
        }.applyAndCheck2(
                stringConcatParameters(),
                Lists.of("hello", "2"),
                "hello2"
        );
    }

    @Test
    public void testApplyAndCheck2FunctionListResultFails() {
        boolean fails = false;

        try {
            new ExpressionFunctionTesting<>() {

                @Override
                public Class<ExpressionFunction<Object, ExpressionFunctionContext>> type() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public ExpressionFunction<Object, ExpressionFunctionContext> createBiFunction() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public ExpressionFunctionContext createContext() {
                    return ExpressionFunctionTestingTest.this.createContext();
                }
            }.applyAndCheck2(
                    stringConcatParameters(),
                    Lists.of("hello", "2"),
                    "fail!!"
            );
        } catch (final Error cause) {
            fails = true;
        }
        this.checkEquals(true, fails);
    }

    private FakeExpressionFunction<Object, ExpressionFunctionContext> stringConcatParameters() {
        return new FakeExpressionFunction<>() {
            @Override
            public String apply(final List<Object> objects,
                                final ExpressionFunctionContext context) {
                return objects.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining());
            }
        };
    }

    // convert.........................................................................................................

    @Test
    public void testConvertStringToString() {
        this.convertAndCheck("hello", String.class, "hello");
    }

    @Test
    public void testConvertObjectToString() {
        this.convertAndCheck(123, String.class, "123");
    }

    @Test
    public void testConvertObjectToBooleanTrue() {
        this.convertAndCheck("true", Boolean.class, Boolean.TRUE);
    }

    @Test
    public void testConvertStringToInteger() {
        this.convertAndCheck("123", Integer.class, 123);
    }

    @Test
    public void testConvertStringToExpressionNumber() {
        this.convertAndCheck("123", ExpressionNumber.class, ExpressionNumberKind.DEFAULT.create(123));
    }

    @Test
    public void testConvertStringToExpressionNumber2() {
        this.convertAndCheck("123.5", ExpressionNumber.class, ExpressionNumberKind.DEFAULT.create(123.5));
    }

    @Test
    public void testConvertObjectToBooleanFalse() {
        this.convertAndCheck("false", Boolean.class, Boolean.FALSE);
    }

    @Test
    public void testConvertIntegerToLongFalse() {
        this.convertAndCheck(123, Long.class, 123L);
    }

    @Test
    public void testConvertIntegerToExpressionNumberFalse() {
        this.convertAndCheck(123, ExpressionNumber.class, ExpressionNumberKind.DEFAULT.create(123));
    }

    private <T> void convertAndCheck(final Object value,
                                     final Class<T> target,
                                     final T expected) {
        this.checkEquals(
                Either.left(expected),
                new ExpressionFunctionTesting<>() {

                    @Override
                    public Class<ExpressionFunction<Object, ExpressionFunctionContext>> type() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public ExpressionFunction<Object, ExpressionFunctionContext> createBiFunction() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public ExpressionFunctionContext createContext() {
                        return ExpressionFunctionTestingTest.this.createContext();
                    }
                }.convert(value, target));
    }

    public ExpressionFunctionContext createContext() {
        return ExpressionFunctionContexts.fake();
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    @Override
    public Class<ExpressionFunctionTesting<?, ?, ?>> type() {
        return Cast.to(ExpressionFunctionTesting.class);
    }
}
