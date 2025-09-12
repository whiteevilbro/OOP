package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.HashMap;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void testDerivative() {
        Expression expression = new Div(new Variable("x"), new Variable("y"));
        assertEquals("(((1*y)-(x*0))/(y*y))", expression.derivative("x").toString());

        expression = new Div(new Variable("x"), new Variable("x"));
        assertEquals("(((1*x)-(x*1))/(x*x))", expression.derivative("x").toString());
    }

    @Test
    void testEval() {
        Expression expression = new Div(new Constant(6), new Constant(3));
        assertEquals(2, expression.eval((HashMap<String, Integer>) null));

        expression = new Div(new Variable("x"), new Constant(3));
        assertEquals(4, expression.eval("x = 13"));
    }

    @Test
    void testSimplifyConstant() {
        Expression expression = new Div(new Constant(6), new Constant(3));
        Expression simplifiedExpression = expression.simplify();
        assertEquals("2", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testSimplify() {
        Expression expression = new Div(new Variable("x"), new Variable("x"));
        Expression simplifiedExpression = expression.simplify();
        assertEquals("1", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Div(new Variable("x"), new Variable("y"));
        simplifiedExpression = expression.simplify();
        assertEquals("(x/y)", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Expression expression = new Div(new Variable("s"), new Constant(3));
        assertEquals("(s/3)", expression.toString());
    }

    @Test
    void testClone() {
        Expression expression = new Div(new Constant(4), new Constant(3));
        Expression clonedExpression = expression.clone();
        assertEquals(expression, clonedExpression);
        assertNotSame(expression, clonedExpression);
    }
}