package expression;

public class BiteAnd extends BinaryOperation {
    public BiteAnd(AnyExpression left, AnyExpression right) {
        super(left, right, "&");
    }

    @Override
    public int getValue(int left, int right) {
        return left & right;
    }
}
