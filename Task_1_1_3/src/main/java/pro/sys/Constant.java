package pro.sys;

/**
 * This class is a constant literal expression.
 */
public final class Constant extends ConstantExpression {

    private final Integer value;

    /**
     * Constructs Const object with given value.
     *
     * @param value Integer to be used as value
     */
    public Constant(Integer value) {
        this.value = value;
    }

    @Override
    public Constant clone() {
        return new Constant(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Expression o) {
        int classNameComparison = this.getClass().getSimpleName()
            .compareTo(o.getClass().getSimpleName());
        if (classNameComparison != 0) {
            return classNameComparison;
        }

        Constant other = (Constant) o;
        return value.compareTo(other.value);
    }

    @Override
    public Constant derivative(String reference) {
        return new Constant(0);
    }

    @Override
    public Constant simplify() {
        return clone();
    }

    @Override
    public Integer eval() {
        return value;
    }
}
