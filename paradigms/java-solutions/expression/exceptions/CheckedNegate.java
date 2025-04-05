package expression.exceptions;

import expression.AnyExpression;

import java.util.List;

public class CheckedNegate implements AnyExpression {
    private final AnyExpression unaryMinus;

    public CheckedNegate(AnyExpression expression) {
        this.unaryMinus = expression;
    }

    @Override
    public int evaluate(int i) throws OverflowException {
        int unaryMin = unaryMinus.evaluate(i);
        if (unaryMin == Integer.MIN_VALUE) {
            throw new OverflowException("The result exceeds the positive range of int, leading to overflow.");
        }
        return -unaryMin;
    }

    @Override
    public int evaluate(int x, int y, int z) throws OverflowException {
        int unaryMin = unaryMinus.evaluate(x, y, z);
        if (unaryMin == Integer.MIN_VALUE) {
            throw new OverflowException("The result exceeds the positive range of int, leading to overflow.");
        }
        return -unaryMin;
    }

    @Override
    public String toString() {
        return "-(" + unaryMinus.toString() + ")";
    }

    @Override
    public int evaluate(List<Integer> variables) {
        int unaryMin = unaryMinus.evaluate(variables);
        if (unaryMin == Integer.MIN_VALUE) {
            throw new OverflowException("The result exceeds the positive range of int, leading to overflow.");
        }
        return -unaryMin;
    }
}