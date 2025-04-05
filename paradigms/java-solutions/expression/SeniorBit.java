package expression;

import java.util.List;
import java.util.Objects;

public class SeniorBit implements AnyExpression {
    private final AnyExpression seniorBit;

    public SeniorBit(AnyExpression expression) {
        this.seniorBit = expression;
    }

    @Override
    public int evaluate(int i) {
        return Integer.numberOfLeadingZeros(seniorBit.evaluate(i));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfLeadingZeros(seniorBit.evaluate(x, y, z));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return Integer.numberOfLeadingZeros(seniorBit.evaluate(variables));
    }

    @Override
    public String toString() {
        return "l0(" + seniorBit.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeniorBit seniorBit = (SeniorBit) o;
        return seniorBit == seniorBit.seniorBit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seniorBit);
    }
}
