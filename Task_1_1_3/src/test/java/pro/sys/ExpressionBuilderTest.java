package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExpressionBuilderTest {

    @Test
    void testBuild() {
        Expression expression = Expression.Builder.build("(1+2)");
        Expression expectedExpression = new Add(new Constant(1), new Constant(2));
        assertEquals(expectedExpression, expression);

        expression = Expression.Builder.build("(1+2/(3+4))");
        expectedExpression = new Add(new Constant(1),
            new Div(new Constant(2), new Add(new Constant(3), new Constant(4))));
        assertEquals(expectedExpression, expression);

        expression = Expression.Builder.build("1+2*x-4");
        expectedExpression = new Add(new Constant(1),
            new Sub(new Mul(new Constant(2), new Variable("x")), new Constant(4)));
        assertEquals(expectedExpression, expression);

        expression = Expression.Builder.build("2*2+4");
        expectedExpression = new Add(new Mul(new Constant(2), new Constant(2)), new Constant(4));
        assertEquals(expectedExpression, expression);

    }
}