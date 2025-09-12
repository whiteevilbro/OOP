package pro.sys;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ConstantExpressionTest {

    @Test
    void testBuild() {
        assertThrows(UnsupportedOperationException.class, ConstantExpression::build);
    }
}