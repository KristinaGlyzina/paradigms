package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements AnyExpression {
    private String variable;
    private int index;

    public Variable(String num) {
        this.variable = num;
    }

    public Variable(int i) {
        this.index = i;
        this.variable = "$" + i;
    }

    @Override
    public int evaluate(int i) {
        return i;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (this.variable) {
            case ("x") -> x;
            case ("y") -> y;
            case ("z") -> z;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return this.variable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable1 = (Variable) o;
        return Objects.equals(variable, variable1.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(index);
    }
}
