package pro.sys;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public final class Variable extends Expression {

    private final String name;

    public Variable(String name) throws InvalidParameterException {
        if (!name.matches("[A-Za-z_]+")) {
            throw new InvalidParameterException();
        }
        this.name = name;
    }

    public static Expression build() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression derivative(String reference) {
        if (name.equals(reference)) {
            return new Constant(1);
        }
        return new Constant(0);
    }

    @Override
    public Integer eval(HashMap<String, Integer> values) {
        Integer val = values.get(name);
        if (val == null) {
            throw new NoSuchElementException();
        }
        return val;
    }

    @Override
    public Expression simplify() {
        return clone();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Expression clone() {
        return new Variable(name);
    }

    @Override
    public int compareTo(Expression o) {
        int classNameComparison = super.compareTo(o);
        if (classNameComparison != 0) {
            return classNameComparison;
        }
        return name.compareTo(((Variable) o).name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.name.equals(((Variable) o).name);
    }
}
