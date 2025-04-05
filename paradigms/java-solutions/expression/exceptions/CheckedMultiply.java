package expression.exceptions;

import expression.AnyExpression;
import expression.BinaryOperation;

public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(AnyExpression left, AnyExpression right) {
        super(left, right, "*");
    }

    @Override
    public int getValue(int left, int right) throws OverflowException {
        if (left < 0 && right < 0 && left * right <= 0) {
            throw new OverflowException("Overflow detected: multiplying " + left + " and " + right);
        } else if ((left < 0 || right < 0) && left != 0 && right != 0 && left * right == 0) {
            throw new OverflowException("Overflow detected: multiplying " + left + " and " + right);
        } else if (right > 0 && !(Integer.MIN_VALUE / right <= left && Integer.MAX_VALUE / right >= left)) {
            throw new OverflowException("Overflow detected: multiplying " + left + " and " + right);
        } else if (right < -1 && !(Integer.MIN_VALUE / right >= left && Integer.MAX_VALUE / right <= left)) {
            throw new OverflowException("Overflow detected: multiplying " + left + " and " + right);
        }
        return left * right;
    }
}
