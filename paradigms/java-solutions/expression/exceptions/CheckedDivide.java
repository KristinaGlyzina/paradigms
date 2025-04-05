package expression.exceptions;

import expression.AnyExpression;
import expression.BinaryOperation;

public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(AnyExpression left, AnyExpression right) {
        super(left, right, "/");
    }

    @Override
    public int getValue(int left, int right) throws DivisionByZeroException {
        if (right == 0) {
            throw new DivisionByZeroException("Division by zero is not allowed.");
        } else if (left < 0 && right < 0 && left / right < 0) {
            throw new OverflowException("Overflow detected when dividing " + left + " by " + right);
        }
        return left / right;
    }
}