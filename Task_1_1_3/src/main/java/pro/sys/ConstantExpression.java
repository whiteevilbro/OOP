package pro.sys;

import java.util.HashMap;

public abstract non-sealed class ConstantExpression extends Expression {

    public static Expression build() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    final public Integer eval(HashMap<String, Integer> values) {
        return eval();
    }

    public abstract Integer eval();

    @Override
    public final boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.eval().equals(((ConstantExpression) o).eval());
    }
}
