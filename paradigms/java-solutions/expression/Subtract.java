package expression;

public class Subtract extends BinaryOperation {
    public Subtract(AnyExpression left, AnyExpression right) {
        super(left, right, "-");
    }

    @Override
    public int getValue(int left, int right) {
        return left - right;
    }
}