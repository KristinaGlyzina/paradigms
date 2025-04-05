package expression;

public class Divide extends BinaryOperation {
    public Divide(AnyExpression left, AnyExpression right) {
        super(left, right, "/");
    }

    @Override
    public int getValue(int left, int right) {
        return left / right;
    }
}