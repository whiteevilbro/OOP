package pro.sys;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperatorTest {

    @Test
    void testBuild() {
        assertThrows(UnsupportedOperationException.class, Operator::build);
    }


}