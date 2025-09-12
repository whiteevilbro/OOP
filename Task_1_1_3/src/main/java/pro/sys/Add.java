package pro.sys;

import java.util.HashMap;

public final class Add extends Operator {

    public Add(Expression left, Expression right) {
        super((new Expression[]{left, right}));
    }

    private Add(Expression[] subExpressions) {
        super(subExpressions);
    }

    @Override
    public Add clone() {
        Expression[] subExpressionsClone = new Expression[subExpressions.length];
        for (int i = 0; i < subExpressions.length; i++) {
            subExpressionsClone[i] = subExpressions[i].clone();
        }
        return new Add(subExpressionsClone);
    }

    @Override
    public String toString() {
        return String.format("(%s+%s)", subExpressions[0].toString(), subExpressions[1].toString());
    }

    @Override
    public Expression derivative(String reference) {
        return new Add(subExpressions[0].derivative(reference),
            subExpressions[1].derivative(reference));
    }

    @Override
    public Integer eval(HashMap<String, Integer> values) {
        return subExpressions[0].eval(values) + subExpressions[1].eval(values);
    }

    @Override
    public Expression simplify() {
        Expression left = subExpressions[0].simplify();
        Expression right = subExpressions[1].simplify();
        if (left instanceof ConstantExpression && right instanceof ConstantExpression) {
            return new Constant(
                ((ConstantExpression) left).eval() + ((ConstantExpression) right).eval());
        }
        if (left.compareTo(right) > 0) {
            Expression temp = left;
            left = right;
            right = temp;
        }
        return new Add(left, right);
    }
}
