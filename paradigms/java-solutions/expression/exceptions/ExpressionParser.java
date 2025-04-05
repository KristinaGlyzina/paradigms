package expression.exceptions;


import expression.*;

import java.util.*;

public class ExpressionParser implements TripleParser, ListParser {
    private final Map<String, Integer> priority = new HashMap<>() {{
        put("(", 0);
        put("|", 70);
        put("^", 80);
        put("&", 90);
        put("+", 100);
        put("-", 100);
        put("*", 200);
        put("/", 200);
        put("!", 300);
        put("~", 300);
        put("l0", 300);
        put("t0", 300);
    }};
    private List<String> values = new ArrayList<>();
    private Deque<String> operations;
    private Deque<AnyExpression> eval;

    private static boolean isNumeric(String strNum) {
        for (int i = 0; i < strNum.length(); i++) {
            if (strNum.charAt(i) == '-') {
                continue;
            }
            if (!Character.isDigit(strNum.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void toConstVariableExpression(String val) {
        if (isNumeric(val)) {
            try {
                eval.push(new Const(Integer.parseInt(val)));
            } catch (NumberFormatException e) {
                throw new ParsingException("Long type numbers cannot using in expression.");
            }
        } else {
            if (values.contains(val)) {
                eval.push(new Variable(values.indexOf(val)));
            } else {
                eval.push(new Variable(val));
            }
        }
    }

    private void toOperationExpression(String op) {
        if (eval.isEmpty()) {
            throw new NoArgumentException("The required argument for evaluation is missing.");
        }
        AnyExpression right = eval.pollFirst();
        switch (op) {
            case "!" -> eval.addFirst(new CheckedNegate(right));
            case "~" -> eval.addFirst(new BiteNegate(right));
            case "l0" -> eval.addFirst(new SeniorBit(right));
            case "t0" -> eval.addFirst(new MinorBit(right));
            default -> {
                if (eval.isEmpty()) {
                    throw new ParsingException("No arguments provided.");
                }
                AnyExpression left = eval.pollFirst();
                switch (op) {
                    case "+" -> eval.addFirst(new CheckedAdd(left, right));
                    case "-" -> eval.addFirst(new CheckedSubtract(left, right));
                    case "/" -> eval.addFirst(new CheckedDivide(left, right));
                    case "*" -> eval.addFirst(new CheckedMultiply(left, right));
                    case "^" -> eval.addFirst(new BiteXor(left, right));
                    case "|" -> eval.addFirst(new BiteOr(left, right));
                    case "&" -> eval.addFirst(new BiteAnd(left, right));
                }
            }
        }
    }

    @Override
    public AnyExpression parse(String expression) {
        char last_symb = 0;
        operations = new ArrayDeque<>();
        eval = new ArrayDeque<>();
        boolean isOperation = true;

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            String valueSymbol = String.valueOf(symbol);
            if (valueSymbol.equals("t") || valueSymbol.equals("l")) {
                StringBuilder sbValue = new StringBuilder();
                sbValue.append(symbol);
                sbValue.append(expression.charAt(++i));
                valueSymbol = sbValue.toString();
            }
            if (Character.isDigit(symbol)) {
                if (last_symb == '0') {
                    throw new ParsingException("Operation isn't exist.");
                }
                StringBuilder number = new StringBuilder();
                number.append(symbol);
                while (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                    i++;
                    number.append(expression.charAt(i));
                }
                String numberStr = number.toString();
                if (!operations.isEmpty() && operations.peekFirst().equals("!") && !numberStr.equals("0")) {
                    operations.pollFirst();
                    toConstVariableExpression("-" + numberStr);
                } else {
                    toConstVariableExpression(numberStr);
                }
                number.setLength(0);
                isOperation = false;
            } else if (symbol == '(') {
                isOperation = true;
                operations.addFirst(valueSymbol);
            } else if (symbol == ')') {
                isOperation = false;
                while (!operations.isEmpty() && !operations.peekFirst().equals("(")) {
                    toOperationExpression(operations.pollFirst());
                }
                if (operations.isEmpty()) {
                    throw new ExtraParenthesisException("Extra parenthesis.");
                }
                operations.pollFirst();
            } else if (priority.containsKey(valueSymbol)) {
                if (symbol == '-' && isOperation) {
                    symbol = '!';
                    valueSymbol = String.valueOf(symbol);
                }
                isOperation = true;

                while (!operations.isEmpty() && priority.get(valueSymbol) <= priority.get(operations.peekFirst()) &&
                        priority.get(valueSymbol) != 300) {
                    toOperationExpression(operations.pollFirst());
                }
                operations.addFirst(valueSymbol);
            } else if (symbol == 'x' || symbol == 'y' || symbol == 'z') {
                if (last_symb == '0') {
                    throw new UndefinedOperationException("Operation isn't exist.");
                }
                toConstVariableExpression(valueSymbol);
                isOperation = false;
            } else if (symbol == '$') {
                StringBuilder str = new StringBuilder("$");
                while (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                    str.append(expression.charAt(i + 1));
                    i++;
                }
                if (!values.contains(str.toString())) {
                    throw new ParsingException("Expression cannot contain characters.");
                }
                toConstVariableExpression(str.toString());
                isOperation = false;
            } else if (!Character.isWhitespace(symbol)) {
                throw new ParsingException("Expression cannot contain characters.");
            }
            last_symb = expression.charAt(i);
        }
        while (!operations.isEmpty()) {
            toOperationExpression(operations.pollFirst());
        }
        if (eval.isEmpty()) {
            throw new EmptyExpressionException("The expression provided for evaluation is empty.");
        }
        if (eval.size() > 1) {
            throw new ParsingException("Expression doesn't contain arithmetical operations.");
        }
        return eval.pollFirst();
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) throws Exception {
        values = variables;
        return parse(expression);
    }
}