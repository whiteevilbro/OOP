package pro.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is an abstract Expression class.
 */
// It would've been generic, but there's no structural typing in java
public abstract sealed class Expression implements Comparable<Expression>, Cloneable permits
    ConstantExpression, Operator, Variable {

    /**
     * Builds expression from given string.
     *
     * @param expression string containing arithmetical expression
     * @return Expression object built from given string
     * @throws InvalidParameterException if given string is not valid arithmetic expression
     */
    // Couldn't come up with expandable solution without some kind of builder class,
    // but it would've to be friend to this class. But there's no friends in Java.
    public static Expression build(String expression) throws InvalidParameterException {
        Stack<Expression> outStack = new Stack<>();
        Stack<String> tempStack = new Stack<>();
        Pattern pattern = Pattern.compile("(\\(|\\)|\\d+|[A-Za-z_]+|[+\\-*/])");
        Matcher matcher = pattern.matcher(expression);

        Map<String, Integer> operatorPriorities = Map.of("+", 2, "*", 1, "/", 1, "-", 2);

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String argument = expression.substring(start, end);

            if (argument.matches("\\d+")) {
                outStack.push(new Constant(Integer.parseInt(argument)));
            } else if (argument.matches("[A-Za-z_]+")) {
                outStack.push(new Variable(argument));
            } else {
                switch (argument) {
                    case "(":
                        tempStack.push("(");
                        break;
                    case ")":
                        while (!tempStack.isEmpty()) {
                            argument = tempStack.pop();
                            if (argument.equals("(")) {
                                break;
                            }
                            processOperator(outStack, argument);
                        }
                        break;
                    case "+":
                    case "-":
                    case "/":
                    case "*":
                        if (!tempStack.isEmpty()) {
                            String op = tempStack.peek();
                            while (!op.equals("(")
                                && operatorPriorities.get(op) < operatorPriorities.get(argument)) {
                                tempStack.pop();
                                processOperator(outStack, op);
                                if (tempStack.isEmpty()) {
                                    break;
                                }
                                op = tempStack.peek();
                            }
                        }
                        tempStack.push(argument);
                        break;
                    default:
                        throw new InvalidParameterException();
                }
            }

        }

        while (!tempStack.isEmpty()) {
            processOperator(outStack, tempStack.pop());
        }

        if (outStack.size() != 1) {
            throw new InvalidParameterException();
        }

        return outStack.pop();
    }

    private static void processOperator(Stack<Expression> expressionStack, String operator)
        throws InvalidParameterException {
        Expression left;
        Expression right;
        try {
            right = expressionStack.pop();
            left = expressionStack.pop();
        } catch (EmptyStackException exception) {
            throw new IllegalArgumentException();
        }
        switch (operator) {
            case "+":
                expressionStack.push(new Add(left, right));
                break;
            case "-":
                expressionStack.push(new Sub(left, right));
                break;
            case "/":
                expressionStack.push(new Div(left, right));
                break;
            case "*":
                expressionStack.push(new Mul(left, right));
                break;
            default:
                throw new InvalidParameterException();
        }
    }

    @Override
    public abstract Expression clone();

    @Override
    public abstract String toString();

    @Override
    public int compareTo(Expression o) {
        return this.getClass().getSimpleName().compareTo(o.getClass().getSimpleName());
    }

    /**
     * Differentiates expression with reference to given variable.
     *
     * @param reference string containig variable name to differetiate with reference to.
     * @return new Expression being derivative of this expression.
     */
    public abstract Expression derivative(String reference);

    /**
     * Evaluates expression using variables' values.
     *
     * @param values HashMap&lt;String, Integer&gt; containing values for each variable present in
     *               expression
     * @return expression value
     * @throws NoSuchElementException if expression contains variable not present in given values
     */
    public abstract Integer eval(HashMap<String, Integer> values) throws NoSuchElementException;

    /**
     * Evaluates expression using variables' values.
     *
     * @param values String containing values for each variable present in expression in format
     *               "((?&lt;name&gt;)\s*=\s*(?&lt;value&gt;)\s*;\s*)*
     *               ((?&lt;name&gt;)\s*=\s*(?&lt;value&gt;)\s*"
     * @return expression value
     * @throws NumberFormatException     if string contain variable value that can't be parsed into
     *                                   Integer
     * @throws NoSuchElementException    if expression contains variable not present in given
     *                                   values
     * @throws InvalidParameterException if string breaks the format
     */
    public Integer eval(String values)
        throws NumberFormatException, NoSuchElementException, InvalidParameterException {
        String[] varValues = values.split("\\s*;\\s*");
        HashMap<String, Integer> valueMap = new HashMap<>();
        for (String var : varValues) {
            if (var.isEmpty()) {
                continue;
            }
            String[] tokens = var.split("\\s*=\\s*", 2);
            if (tokens.length != 2) {
                throw new InvalidParameterException("Invalid token: " + var);
            }
            valueMap.put(tokens[0], Integer.valueOf(tokens[1]));
        }
        return eval(valueMap);
    }

    // I didn't want to include following methods, but they're required in the task

    /**
     * Prints expression to System out.
     *
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("unused")
    public final void print() throws IOException {
        print(System.out);
    }

    /**
     * Writes expression in string format to given stream.
     *
     * @param io output stream to write to
     * @throws IOException if an I/O error occurs
     */
    public final void print(OutputStream io) throws IOException {
        io.write(toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Partially evaluates and simplifies expression.
     *
     * @return new simplified expression
     */
    public abstract Expression simplify();
}
