package pro.sys;

/**
 * This class is an abstract Operator class.
 */
public abstract non-sealed class Operator extends Expression {

    protected final Expression[] subExpressions;

    protected Operator(Expression[] subExpressions) {
        this.subExpressions = subExpressions;
    }

    @Override
    public int compareTo(Expression o) throws NullPointerException {
        int classNameComparison = super.compareTo(o);
        if (classNameComparison != 0) {
            return classNameComparison;
        }

        Operator other = (Operator) o;
        if (subExpressions.length != other.subExpressions.length) {
            throw new IllegalArgumentException();
        }

        int subExpressionComparison;
        for (int i = 0; i < subExpressions.length; i++) {
            subExpressionComparison = subExpressions[i].compareTo(other.subExpressions[i]);
            if (subExpressionComparison != 0) {
                return subExpressionComparison;
            }
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) throws IllegalArgumentException {

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Operator other = (Operator) o;
        if (subExpressions.length != other.subExpressions.length) {
            return false;
        }

        boolean isSubExpressionEqual = true;
        for (int i = 0; (i < subExpressions.length) && isSubExpressionEqual; i++) {
            isSubExpressionEqual = subExpressions[i].equals(other.subExpressions[i]);
        }

        return isSubExpressionEqual;
    }
}
