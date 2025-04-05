package expression;

public class Multiply extends BinaryOperation {
    public Multiply(AnyExpression left, AnyExpression right) {
        super(left, right, "*");
    }

    @Override
    public int getValue(int left, int right) {
        return left * right;
    }
}