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
        assertEquals("(0-1)", derivative.toString());
    }

    @Test
    void testDerivativeConstant() {
        Expression expression = new Sub(new Constant(4), new Constant(3));
        Expression derivative = expression.derivative("x");
        assertEquals("(0-0)", derivative.toString());
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
        assertEquals("0", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Sub(new Variable("s"), new Constant(3));
        simplifiedExpression = expression.simplify();
        assertEquals("(s-3)", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Sub(new Constant(3), new Variable("s"));
        simplifiedExpression = expression.simplify();
        assertEquals("(3-s)", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Sub(
            new Sub(new Variable("a"), new Variable("c")),
            new Sub(new Variable("b"), new Variable("d"))
        );
        simplifiedExpression = expression.simplify();
        assertEquals("((a-c)-(b-d))", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testSimplifyConstant() {
        Expression expression = new Sub(new Constant(4), new Constant(3));
        Expression simplifiedExpression = expression.simplify();
        assertEquals("1", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Expression expression = new Sub(new Variable("s"), new Constant(3));
        assertEquals("(s-3)", expression.toString());
    }
}