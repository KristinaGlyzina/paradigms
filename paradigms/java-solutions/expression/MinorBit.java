package expression;

import java.util.List;
import java.util.Objects;

public class MinorBit implements AnyExpression {
    private final AnyExpression minorBit;

    public MinorBit(AnyExpression expression) {
        this.minorBit = expression;
    }

    @Override
    public int evaluate(int i) {
        return Integer.numberOfTrailingZeros(minorBit.evaluate(i));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfTrailingZeros(minorBit.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return Integer.numberOfTrailingZeros(minorBit.evaluate(variables));
    }

    @Override
    public String toString() {
        return "t0(" + minorBit.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinorBit minorBit = (MinorBit) o;
        return minorBit == minorBit.minorBit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minorBit);
    }
}
