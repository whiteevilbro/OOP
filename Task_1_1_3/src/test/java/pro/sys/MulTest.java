package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.HashMap;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void testClone() {
        Expression expression = new Mul(new Constant(4), new Constant(3));
        Expression clonedExpression = expression.clone();
        assertEquals(expression, clonedExpression);
        assertNotSame(expression, clonedExpression);
    }

    @Test
    void testDerivative() {
        Expression expression = new Mul(new Variable("x"),
            new Add(new Variable("x"), new Variable("y")));
        Expression expectedExpression = new Add(
            new Mul(new Variable("x"), new Add(new Constant(1), new Constant(0))),
            new Mul(new Constant(1), new Add(new Variable("x"), new Variable("y"))));
        assertEquals(expectedExpression, expression.derivative("x"));

        expectedExpression = new Add(
            new Mul(new Variable("x"), new Add(new Constant(0), new Constant(1))),
            new Mul(new Constant(0), new Add(new Variable("x"), new Variable("y"))));
        assertEquals(expectedExpression, expression.derivative("y"));
    }

    @Test
    void testEval() {
        Expression expression = new Mul(new Constant(4), new Constant(3));
        assertEquals(Integer.valueOf(12), expression.eval((HashMap<String, Integer>) null));
    }

    @Test
    void testSimplify() {
        Expression expression = new Mul(new Mul(new Variable("b"), new Variable("d")),
            new Mul(new Variable("a"), new Variable("c")));
        Expression simplifiedExpression = expression.simplify();

        Expression expectedExpression = new Mul(new Mul(new Variable("a"), new Variable("c")),
            new Mul(new Variable("b"), new Variable("d")));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(new Mul(new Variable("a"), new Variable("b")),
            new Mul(new Variable("c"), new Variable("d")));
        simplifiedExpression = expression.simplify();

        expectedExpression = new Mul(new Mul(new Variable("a"), new Variable("b")),
            new Mul(new Variable("c"), new Variable("d")));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(new Constant(0), new Variable("x"));
        simplifiedExpression = expression.simplify();
        assertEquals(new Constant(0), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(new Constant(1), new Mul(new Variable("x"), new Constant(3)));
        simplifiedExpression = expression.simplify();

        expectedExpression = new Mul(new Constant(3), new Variable("x"));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(new Variable("x"), new Constant(0));
        simplifiedExpression = expression.simplify();
        assertEquals(new Constant(0), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(new Mul(new Constant(3), new Variable("x")), new Constant(1));
        simplifiedExpression = expression.simplify();
        expectedExpression = new Mul(new Constant(3), new Variable("x"));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testSimplifyConstant() {
        Expression expression = new Mul(new Constant(4), new Constant(3));
        Expression simplifiedExpression = expression.simplify();
        assertEquals(new Constant(12), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Expression expression = new Mul(new Variable("t"), new Variable("g"));
        assertEquals("(t*g)", expression.toString());
    }
}