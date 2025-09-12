package pro.sys;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * This class is an abstract Variable class.
 */
public final class Variable extends Expression {

    private final String name;

    /**
     * Constructs Variable object with given name.
     *
     * @param name name of the variable consisting of latin letters and underscores
     * @throws InvalidParameterException if name breaks format
     */
    public Variable(String name) throws InvalidParameterException {
        if (!name.matches("[A-Za-z_]+")) {
            throw new InvalidParameterException();
        }
        this.name = name;
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException if called
     */
    public static void build() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression clone() {
        return new Variable(name);
    }

    @Override
    public String toString() {
        return name;
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
    public Expression derivative(String reference) {
        if (name.equals(reference)) {
            return new Constant(1);
        }
        return new Constant(0);
    }

    @Override
    public Integer eval(HashMap<String, Integer> values) throws NoSuchElementException {
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.name.equals(((Variable) o).name);
    }
}
