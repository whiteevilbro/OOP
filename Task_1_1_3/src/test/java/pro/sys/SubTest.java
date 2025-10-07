package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.HashMap;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void testClone() {
        Expression expression = new Sub(new Constant(4), new Constant(3));
        Expression clonedExpression = expression.clone();
        assertEquals(expression, clonedExpression);
        assertNotSame(expression, clonedExpression);
    }

    @Test
    void testDerivative() {
        Expression expression = new Sub(new Constant(4), new Variable("x"));
        Expression derivative = expression.derivative("x");
        Expression expectedExpression = new Sub(new Constant(0), new Constant(1));
        assertEquals(expectedExpression, derivative);
    }

    @Test
    void testDerivativeConstant() {
        Expression expression = new Sub(new Constant(4), new Constant(3));
        Expression derivative = expression.derivative("x");
        Expression expectedExpression = new Sub(new Constant(0), new Constant(0));
        assertEquals(expectedExpression, derivative);
    }

    @Test
    void testEval() {
        Expression expression = new Sub(new Constant(4), new Constant(3));
        assertEquals(Integer.valueOf(1), expression.eval((HashMap<String, Integer>) null));
    }

    @Test
    void testSimplify() {
        Expression expression = new Sub(new Variable("x"), new Variable("x"));
        Expression simplifiedExpression = expression.simplify();

        Expression expectedExpression = new Constant(0);
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Sub(new Variable("s"), new Constant(3));
        simplifiedExpression = expression.simplify();
        expectedExpression = new Sub(new Variable("s"), new Constant(3));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Sub(new Constant(3), new Variable("s"));
        simplifiedExpression = expression.simplify();
        expectedExpression = new Sub(new Constant(3), new Variable("s"));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Sub(new Sub(new Variable("a"), new Variable("c")),
            new Sub(new Variable("b"), new Variable("d")));
        simplifiedExpression = expression.simplify();
        expectedExpression = new Sub(new Sub(new Variable("a"), new Variable("c")),
            new Sub(new Variable("b"), new Variable("d")));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        Expression subExpression1 = new Add(new Variable("x"), new Variable("y"));
        Expression subExpression2 = new Add(new Variable("y"), new Variable("x"));
        expression = new Sub(subExpression1, subExpression2);
        assertEquals(new Constant(0), expression.simplify());
    }

    @Test
    void testSimplifyConstant() {
        Expression expression = new Sub(new Constant(4), new Constant(3));
        Expression simplifiedExpression = expression.simplify();
        assertEquals(new Constant(1), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Expression expression = new Sub(new Variable("s"), new Constant(3));
        assertEquals("(s-3)", expression.toString());
    }
}