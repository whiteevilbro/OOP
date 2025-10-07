package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.HashMap;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void testClone() {
        Expression expression = new Div(new Constant(4), new Constant(3));
        Expression clonedExpression = expression.clone();
        assertEquals(expression, clonedExpression);
        assertNotSame(expression, clonedExpression);
    }

    @Test
    void testDerivative() {
        Expression expression = new Div(new Variable("x"), new Variable("y"));
        Expression expectedExpression = new Div(
            new Sub(
                new Mul(new Constant(1), new Variable("y")),
                new Mul(new Variable("x"), new Constant(0))
            ),
            new Mul(new Variable("y"), new Variable("y"))
        );

        assertEquals(expectedExpression, expression.derivative("x"));

        expression = new Div(new Variable("x"), new Variable("x"));
        expectedExpression = new Div(
            new Sub(
                new Mul(new Constant(1), new Variable("x")),
                new Mul(new Variable("x"), new Constant(1))
            ),
            new Mul(new Variable("x"), new Variable("x"))
        );
        assertEquals(expectedExpression, expression.derivative("x"));
    }

    @Test
    void testEval() {
        Expression expression = new Div(new Constant(6), new Constant(3));
        assertEquals(2, expression.eval((HashMap<String, Integer>) null));

        expression = new Div(new Variable("x"), new Constant(3));
        assertEquals(4, expression.eval("x = 13"));
    }

    @Test
    void testSimplify() {
        Expression expression = new Div(new Variable("x"), new Variable("x"));
        Expression simplifiedExpression = expression.simplify();
        assertEquals(new Constant(1), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Div(new Variable("x"), new Variable("y"));
        simplifiedExpression = expression.simplify();
        Expression expectedExpression = new Div(new Variable("x"), new Variable("y"));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testSimplifyConstant() {
        Expression expression = new Div(new Constant(6), new Constant(3));
        Expression simplifiedExpression = expression.simplify();
        assertEquals(new Constant(2), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Expression expression = new Div(new Variable("s"), new Constant(3));
        assertEquals("(s/3)", expression.toString());
    }
}