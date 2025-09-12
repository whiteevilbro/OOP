package pro.sys;

import java.util.HashMap;

public final class Mul extends Operator {

    public Mul(Expression left, Expression right) {
        super((new Expression[]{left, right}));
    }

    private Mul(Expression[] subExpressions) {
        super(subExpressions);
    }

    @Override
    public Expression clone() {
        Expression[] subExpressionsClone = new Expression[subExpressions.length];
        for (int i = 0; i < subExpressions.length; i++) {
            subExpressionsClone[i] = subExpressions[i].clone();
        }
        return new Mul(subExpressionsClone);
    }

    @Override
    public String toString() {
        return String.format("(%s*%s)", subExpressions[0].toString(), subExpressions[1].toString());
    }

    @Override
    public Expression derivative(String reference) {
        return new Add(new Mul(subExpressions[0].clone(), subExpressions[1].derivative(reference)),
            new Mul(subExpressions[0].derivative(reference), subExpressions[1].clone()));
    }

    @Override
    public Integer eval(HashMap<String, Integer> values) {
        return subExpressions[0].eval(values) * subExpressions[1].eval(values);
    }

    @Override
    public Expression simplify() {
        Expression left = subExpressions[0].simplify();
        Expression right = subExpressions[1].simplify();

        if (left instanceof ConstantExpression) {
            if (right instanceof ConstantExpression) {
                return new Constant(
                    ((ConstantExpression) left).eval() * ((ConstantExpression) right).eval());
            } else {
                Integer leftValue = ((ConstantExpression) left).eval();
                if (leftValue.equals(0)) {
                    return new Constant(0);
                } else if (leftValue.equals(1)) {
                    return right;
                }
            }
        } else {
            if (right instanceof ConstantExpression) {
                Integer rightValue = ((ConstantExpression) right).eval();
                if (rightValue.equals(0)) {
                    return new Constant(0);
                } else if (rightValue.equals(1)) {
                    return left;
                }
            }
        }

        if (left.compareTo(right) > 0) {
            Expression temp = left;
            left = right;
            right = temp;
        }

        return new Mul(left, right);
    }
}
