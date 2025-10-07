package pro.sys;

import java.util.HashMap;

/**
 * This class is an abstract Constant Expression class. All subclasses of this class should be
 * capable of evaluating without any variables' values.
 */
public abstract non-sealed class ConstantExpression extends Expression {

    @Override
    public final boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.eval().equals(((ConstantExpression) o).eval());
    }

    /**
     * Evaluates expression without variables' values.
     *
     * @return expression value
     */
    public abstract Integer eval();

    @Override
    public final Integer eval(HashMap<String, Integer> values) {
        return eval();
    }
}
