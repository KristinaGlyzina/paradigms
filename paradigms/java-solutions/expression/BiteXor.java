package expression;

public class BiteXor extends BinaryOperation {
    public BiteXor(AnyExpression left, AnyExpression right) {
        super(left, right, "^");
    }

    @Override
    public int getValue(int left, int right) {
        return left ^ right;
    }
}