package expression.exceptions;

import expression.AnyExpression;
import expression.BinaryOperation;

public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(AnyExpression left, AnyExpression right) {
        super(left, right, "+");
    }

    @Override
    public int getValue(int left, int right) throws OverflowException {
        if (left < 0 && right < 0 && left + right >= 0) {
            throw new OverflowException("Overflow when adding two negative values: " + left + " + " + right);
        } else if (left > 0 && right > 0 && left + right <= 0) {
            throw new OverflowException("Overflow when adding two positive values: " + left + " + " + right);
        }
        return left + right;
    }
}