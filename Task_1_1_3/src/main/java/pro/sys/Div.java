package pro.sys;

import java.util.HashMap;

public final class Div extends Operator {

    public Div(Expression numerator, Expression denominator) {
        super((new Expression[]{numerator, denominator}));
    }

    private Div(Expression[] subExpressions) {
        super(subExpressions);
    }

    @Override
    public Expression derivative(String reference) {
        return new Div(
            new Sub(
                new Mul(subExpressions[0].derivative(reference), subExpressions[1].clone()),
                new Mul(subExpressions[0].clone(), subExpressions[1].derivative(reference))
            ),
            new Mul(subExpressions[1].clone(), subExpressions[1].clone())
        );
    }

    @Override
    public Integer eval(HashMap<String, Integer> values) {
        return subExpressions[0].eval(values) / subExpressions[1].eval(values);
    }

    @Override
    public Expression simplify() {
        Expression left = subExpressions[0].simplify();
        Expression right = subExpressions[1].simplify();

        if (left instanceof ConstantExpression && right instanceof ConstantExpression) {
            return new Constant(
                ((ConstantExpression) left).eval() / ((ConstantExpression) right).eval());
        }
        if (left.equals(right)) {
            return new Constant(1);
        }

        return new Div(left, right);
    }

    @Override
    public String toString() {
        return String.format("(%s/%s)", subExpressions[0].toString(), subExpressions[1].toString());
    }

    @Override
    public Expression clone() {
        Expression[] subExpressionsClone = new Expression[subExpressions.length];
        for (int i = 0; i < subExpressions.length; i++) {
            subExpressionsClone[i] = subExpressions[i].clone();
        }
        return new Div(subExpressionsClone);
    }
}
