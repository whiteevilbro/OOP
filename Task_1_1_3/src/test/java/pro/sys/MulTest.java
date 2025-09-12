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
        Expression expression = new Mul(
            new Variable("x"),
            new Add(new Variable("x"), new Variable("y"))
        );
        assertEquals("((x*(1+0))+(1*(x+y)))", expression.derivative("x").toString());
        assertEquals("((x*(0+1))+(0*(x+y)))", expression.derivative("y").toString());
    }

    @Test
    void testEval() {
        Expression expression = new Mul(new Constant(4), new Constant(3));
        assertEquals(Integer.valueOf(12), expression.eval((HashMap<String, Integer>) null));
    }

    @Test
    void testSimplify() {
        Expression expression = new Mul(
            new Mul(new Variable("b"), new Variable("d")),
            new Mul(new Variable("a"), new Variable("c"))
        );
        Expression simplifiedExpression = expression.simplify();
        assertEquals("((a*c)*(b*d))", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(
            new Mul(new Variable("a"), new Variable("b")),
            new Mul(new Variable("c"), new Variable("d"))
        );
        simplifiedExpression = expression.simplify();
        assertEquals("((a*b)*(c*d))", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(
            new Constant(0),
            new Variable("x")
        );
        simplifiedExpression = expression.simplify();
        assertEquals("0", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(
            new Constant(1),
            new Mul(new Variable("x"), new Constant(3))
        );
        simplifiedExpression = expression.simplify();
        assertEquals("(3*x)", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(
            new Variable("x"),
            new Constant(0)
        );
        simplifiedExpression = expression.simplify();
        assertEquals("0", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);

        expression = new Mul(
            new Mul(new Constant(3), new Variable("x")),
            new Constant(1)
        );
        simplifiedExpression = expression.simplify();
        assertEquals("(3*x)", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testSimplifyConstant() {
        Expression expression = new Mul(new Constant(4), new Constant(3));
        Expression simplifiedExpression = expression.simplify();
        assertEquals("12", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Expression expression = new Mul(new Variable("t"), new Variable("g"));
        assertEquals("(t*g)", expression.toString());
    }
}