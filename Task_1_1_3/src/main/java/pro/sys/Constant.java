package pro.sys;

final public class Constant extends ConstantExpression {

    final private Integer value;

    public Constant(Integer value) {
        this.value = value;
    }

    @Override
    public Integer eval() {
        return value;
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
    public String toString() {
        return value.toString();
    }

    @Override
    public Constant clone() {
        return new Constant(value);
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
}
