package pro.sys;

import java.util.HashMap;

/**
 * This class is a subtraction operator expression.
 */
public final class Sub extends Operator {

    /**
     * Constructs Sub object with two given subexpressions.
     *
     * @param minuend    expression to be used as minuend
     * @param subtrahend expression to be used as subtrahend
     */
    public Sub(Expression minuend, Expression subtrahend) {
        super((new Expression[]{minuend, subtrahend}));
    }

    private Sub(Expression[] subExpressions) {
        super(subExpressions);
    }

    @Override
    public Sub clone() {
        Expression[] subExpressionsClone = new Expression[subExpressions.length];
        for (int i = 0; i < subExpressions.length; i++) {
            subExpressionsClone[i] = subExpressions[i].clone();
        }
        return new Sub(subExpressionsClone);
    }

    @Override
    public String toString() {
        return String.format("(%s-%s)", subExpressions[0].toString(), subExpressions[1].toString());
    }

    @Override
    public Expression derivative(String reference) {
        return new Sub(subExpressions[0].derivative(reference),
            subExpressions[1].derivative(reference));
    }

    @Override
    public Integer eval(HashMap<String, Integer> values) {
        return subExpressions[0].eval(values) - subExpressions[1].eval(values);
    }

    @Override
    public Expression simplify() {
        Expression left = subExpressions[0].simplify();
        Expression right = subExpressions[1].simplify();

        if (left instanceof ConstantExpression && right instanceof ConstantExpression) {
            return new Constant(
                ((ConstantExpression) left).eval() - ((ConstantExpression) right).eval());
        }
        if (left.equals(right)) {
            return new Constant(0);
        }

        return new Sub(left, right);
    }
}
