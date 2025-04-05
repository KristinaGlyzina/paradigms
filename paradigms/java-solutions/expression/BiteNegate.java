package expression;

import java.util.List;
import java.util.Objects;

public class BiteNegate implements AnyExpression {
    private final AnyExpression biteNegate;

    public BiteNegate(AnyExpression expression) {
        this.biteNegate = expression;
    }

    @Override
    public int evaluate(int i) {
        return ~biteNegate.evaluate(i);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return ~biteNegate.evaluate(x, y, z);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return ~biteNegate.evaluate(variables);
    }

    @Override
    public String toString() {
        return "~(" + biteNegate.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiteNegate biteNegate = (BiteNegate) o;
        return biteNegate == biteNegate.biteNegate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(biteNegate);
    }
}