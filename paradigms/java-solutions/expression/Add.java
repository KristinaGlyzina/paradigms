package expression;

public class Add extends BinaryOperation {
    public Add(AnyExpression left, AnyExpression right) {
        super(left, right, "+");
    }

    @Override
    public int getValue(int left, int right) {
        return left + right;
    }
}