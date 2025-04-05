package expression;

public class BiteOr extends BinaryOperation {
    public BiteOr(AnyExpression left, AnyExpression right) {
        super(left, right, "|");
    }

    @Override
    public int getValue(int left, int right) {
        return left | right;
    }
}
