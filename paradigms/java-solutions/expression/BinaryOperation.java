package expression;

import java.util.List;
import java.util.Objects;

public abstract class BinaryOperation implements AnyExpression {
    protected final String operation;
    protected final AnyExpression left;
    protected final AnyExpression right;

    protected BinaryOperation(AnyExpression left, AnyExpression right, String operation) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operation + " " + right.toString() + ")";
    }

    @Override
    public int evaluate(int i) {
        return getValue(left.evaluate(i), right.evaluate(i));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return getValue(left.evaluate(variables), right.evaluate(variables));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getValue(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    public int getValue(int evaluate, int evaluate1) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperation that = (BinaryOperation) o;
        return Objects.equals(operation, that.operation) && Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, operation);
    }
}