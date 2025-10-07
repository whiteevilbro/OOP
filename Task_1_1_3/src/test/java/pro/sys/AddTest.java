package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void testClone() {
        Expression expression = new Add(new Constant(4), new Constant(3));
        Expression clonedExpression = expression.clone();
        assertEquals(expression, clonedExpression);
        assertNotSame(expression, clonedExpression);
    }

    @Test
    void testCompareTo() {
        Add addOne = new Add(new Variable("x"), new Constant(3));
        Add addTwo = new Add(new Variable("x"), new Constant(3));
        Add addThree = new Add(new Variable("x"), new Constant(4));

        assertTrue(addOne.compareTo(addThree) < 0);
        assertTrue(addThree.compareTo(addOne) > 0);
        assertEquals(0, addOne.compareTo(addTwo));

        Variable variable = new Variable("x");
        assertTrue(addOne.compareTo(variable) < 0);
    }

    @Test
    void testDerivative() {
        Expression expression = new Add(new Constant(4), new Variable("x"));
        Expression derivative = expression.derivative("x");
        Expression expectedExpression = new Add(new Constant(0), new Constant(1));
        assertEquals(expectedExpression, derivative);
    }

    @Test
    void testDerivativeConstant() {
        Expression expression = new Add(new Constant(4), new Constant(3));
        Expression derivative = expression.derivative("x");
        Expression expectedExpression = new Add(new Constant(0), new Constant(0));
        assertEquals(expectedExpression, derivative);
    }

    @Test
    @Order(Order.DEFAULT / 4 * 3)
    void testEquals() {
        Expression addOne = new Add(new Variable("x"), new Constant(3));
        Expression addTwo = new Add(new Variable("x"), new Constant(3));
        assertEquals(addOne, addTwo);
        Expression addThree = new Add(new Variable("x"), new Constant(4));
        assertNotEquals(addOne, addThree);
        Expression variable = new Variable("x");
        assertNotEquals(addOne, variable);

        assertNotEquals(null, addOne);
    }

    @Test
    void testEval() {
        Expression expression = new Add(new Constant(4), new Constant(3));
        assertEquals(Integer.valueOf(7), expression.eval((HashMap<String, Integer>) null));
    }

    @Test
    void testSimplify() {
        Expression expression = new Add(new Add(new Variable("b"), new Variable("d")),
            new Add(new Variable("a"), new Variable("c")));
        Expression simplifiedExpression = expression.simplify();
        Expression expectedExpression = new Add(new Add(new Variable("a"), new Variable("c")),
            new Add(new Variable("b"), new Variable("d")));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Add(new Variable("s"), new Constant(3));
        simplifiedExpression = expression.simplify();
        expectedExpression = new Add(new Constant(3), new Variable("s"));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);

        expression = new Add(new Constant(3), new Variable("s"));
        simplifiedExpression = expression.simplify();
        expectedExpression = new Add(new Constant(3), new Variable("s"));
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testSimplifyConstant() {
        Expression expression = new Add(new Constant(4), new Constant(3));
        Expression simplifiedExpression = expression.simplify();
        Expression expectedExpression = new Constant(7);
        assertEquals(expectedExpression, simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Expression expression = new Add(new Add(new Variable("b"), new Variable("d")),
            new Add(new Variable("a"), new Variable("c")));
        assertEquals("((b+d)+(a+c))", expression.toString());
    }
}