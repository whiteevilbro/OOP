package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void testBuild() {
        Expression expression = Expression.build("(1+2)");
        assertEquals("(1+2)", expression.toString());

        expression = Expression.build("(1+2/(3+4))");
        assertEquals("(1+(2/(3+4)))", expression.toString());

        expression = Expression.build("1+2*x-4");
        assertEquals("(1+((2*x)-4))", expression.toString());

        expression = Expression.build("2*2+4");
        assertEquals("((2*2)+4)", expression.toString());

    }
}