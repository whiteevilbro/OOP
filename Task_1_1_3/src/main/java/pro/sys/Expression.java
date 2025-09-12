package pro.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// It would've been generic, but there's no structural typing in java
public sealed abstract class Expression implements Comparable<Expression>, Cloneable permits
    ConstantExpression, Operator, Variable {

    private static void processOperator(Stack<Expression> expressionStack, String operator)
        throws InvalidParameterException {
        Expression left, right;
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
        }
    }

    // Couldn't come up with expandable solution without some kind of builder class, but it would've to be friend to this class. But there's no friends in Java
    public static Expression build(String expression) throws InvalidParameterException {
        Stack<Expression> outStack = new Stack<>();
        Stack<String> tempStack = new Stack<>();
        Pattern pattern = Pattern.compile("(\\(|\\)|\\d+|[A-Za-z_]+|[+\\-*/])");
        Matcher matcher = pattern.matcher(expression);

        Map<String, Integer> operatorPriorities = Map.of(
            "+", 2,
            "*", 1,
            "/", 1,
            "-", 2
        );

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
                            while (!op.equals("(") &&
                                operatorPriorities.get(op) < operatorPriorities.get(argument)) {
                                tempStack.pop();
                                processOperator(outStack, op);
                                if (tempStack.isEmpty()) {
                                    break;
                                }
                                op = tempStack.peek();
                            }
                        }
                        tempStack.push(argument);
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

    public abstract Expression derivative(String reference);

    public abstract Integer eval(HashMap<String, Integer> values);

    public abstract Expression simplify();

    @Override
    public abstract String toString();

    @Override
    public int compareTo(Expression o) {
        return this.getClass().getSimpleName().compareTo(o.getClass().getSimpleName());
    }

    /*
    @Override
    public boolean equals(Object o) {
        switch (this) {
            case Operator operator -> {
                return operator.equals(o);
            }
            case ConstantExpression constantExpression -> {
                return constantExpression.equals(o);
            }
            case Variable variable -> {
                return variable.equals(o);
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
     */

    @Override
    public abstract Expression clone();

    // I didn't want to include following methods, but they're required in the task

    public Integer eval(String values) throws NumberFormatException {
        String[] varValues = values.split("\\s*;\\s*");
        HashMap<String, Integer> valueMap = new HashMap<>();
        for (String var : varValues) {
            if (var.isEmpty()) {
                continue;
            }
            String[] tokens = var.split("\\s*=\\s*", 2);
            if (tokens.length != 2) {
                throw new IllegalArgumentException("Invalid token: " + var);
            }
            valueMap.put(tokens[0], Integer.valueOf(tokens[1]));
        }
        return eval(valueMap);
    }

    public final void print() throws IOException {
        print(System.out);
    }

    public final void print(OutputStream io) throws IOException {
        io.write(toString().getBytes(StandardCharsets.UTF_8));
    }
}
