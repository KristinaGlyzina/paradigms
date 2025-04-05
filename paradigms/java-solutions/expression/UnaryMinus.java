package expression;

import java.util.List;
import java.util.Objects;

public class UnaryMinus implements AnyExpression {
    private final AnyExpression unaryMinus;

    public UnaryMinus(AnyExpression expression) {
        this.unaryMinus = expression;
    }

    @Override
    public int evaluate(int i) {
        return -unaryMinus.evaluate(i);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -unaryMinus.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return -unaryMinus.evaluate(variables);
    }

    @Override
    public String toString() {
        return "-(" + unaryMinus.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnaryMinus unaryMinus = (UnaryMinus) o;
        return unaryMinus == unaryMinus.unaryMinus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unaryMinus);
    }
}