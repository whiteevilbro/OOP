package pro.sys;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void testClone() {
        Variable expression = new Variable("x");
        Expression simplifiedExpression = expression.simplify();
        assertEquals(expression, simplifiedExpression);
        assertEquals(new Variable("x"), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    void testCompareTo() {
        Variable expressionOne = new Variable("x");
        Variable expressionTwo = new Variable("y");
        Expression expressionThree = new Constant(1);
        assertTrue(expressionOne.compareTo(expressionTwo) < 0);
        assertTrue(expressionTwo.compareTo(expressionOne) > 0);
        assertTrue(expressionOne.compareTo(expressionThree) > 0);
    }

    @Test
    void testDerivative() {
        Variable variable = new Variable("x");
        assertEquals(new Constant(1), variable.derivative("x"));
        assertEquals(new Constant(0), variable.derivative("y"));
    }

    @Test
    @Order(Order.DEFAULT / 4 * 3)
    void testEquals() {
        Variable expressionOne = new Variable("x");
        Variable expressionTwo = new Variable("x");
        assertEquals(expressionOne, expressionTwo);
        Variable expressionThree = new Variable("y");
        assertNotEquals(expressionOne, expressionThree);
        Expression expressionFour = new Constant(1);
        assertNotEquals(expressionOne, expressionFour);

        assertNotEquals(null, expressionOne);
    }

    @Test
    void testEval() {
        Variable variable = new Variable("x");
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 4);
        assertEquals(4, variable.eval(map));
        assertEquals(4, variable.eval("x = 4"));
        assertEquals(4, variable.eval("x = 4; y = 8"));
        assertEquals(4, variable.eval("y = 8; x = 4"));
        assertEquals(4, variable.eval("y = 8; x = 4; z = 5"));
        assertEquals(4, variable.eval("y = 8; x = 4; z = 5;"));

        assertThrows(NoSuchElementException.class, () -> variable.eval(""));
    }

    @Test
    void testSimplify() {
        Variable expression = new Variable("x");
        Expression simplifiedExpression = expression.simplify();
        assertEquals(expression, simplifiedExpression);
        assertEquals(new Variable("x"), simplifiedExpression);
        assertNotSame(expression, simplifiedExpression);
    }

    @Test
    @Order(Order.DEFAULT / 2)
    void testToString() {
        Variable expression = new Variable("x");
        assertEquals("x", expression.toString());
    }

    @Test
    void testVariable() {
        assertDoesNotThrow(() -> new Variable("vArIaBlE_NaMe"));
        assertThrows(IllegalArgumentException.class, () -> new Variable("9/11"));
    }
}