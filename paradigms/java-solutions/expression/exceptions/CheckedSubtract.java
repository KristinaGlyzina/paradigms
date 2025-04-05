package expression.exceptions;

import expression.AnyExpression;
import expression.BinaryOperation;

public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(AnyExpression left, AnyExpression right) {
        super(left, right, "-");
    }

    @Override
    public int getValue(int left, int right) throws OverflowException {
        if (left - right >= 0 && left < right) {
            throw new OverflowException("Overflow detected: subtracting " + right + " from " + left);
        }
        if (left - right < 0 && left > right) {
            throw new OverflowException("Overflow detected: subtracting " + right + " from " + left);
        }
        return left - right;
    }
}
