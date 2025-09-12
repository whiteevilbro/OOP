package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class ConstantTest {

    @Test
    void testClone() {
        Constant expression = new Constant(4);
        Expression simplifiedExpression = expression.simplify();
        assertEquals(expression, simplifiedExpression);
        assertEquals("4", expression.toString());
        assertEquals("4", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testCompareTo() {
        Constant constantOne = new Constant(4);
        Constant constantTwo = new Constant(3);
        Expression expression = new Variable("x");
        assertTrue(constantOne.compareTo(constantTwo) > 0);
        assertTrue(constantOne.compareTo(expression) < 0);
    }

    @Test
    void testDerivative() {
        Constant expression = new Constant(4);
        assertEquals("0", expression.derivative("").toString());
    }

    @Test
    @Order(Order.DEFAULT / 4 * 3)
    void testEquals() {
        Constant constantOne = new Constant(4);
        Constant constantTwo = new Constant(4);
        assertEquals(constantOne, constantTwo);
        //noinspection SimplifiableAssertion,ConstantValue
        assertFalse(constantOne.equals(null));
    }

    @Test
    void testEval() {
        Constant expression = new Constant(4);
        assertEquals(4, expression.eval());
    }

    @Test
    void testSimplify() {
        Constant expression = new Constant(4);
        Expression simplifiedExpression = expression.simplify();
        assertEquals(expression, simplifiedExpression);
        assertEquals("4", simplifiedExpression.toString());
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Constant expression = new Constant(4);
        assertEquals("4", expression.toString());
    }
}