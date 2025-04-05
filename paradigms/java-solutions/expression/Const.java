package expression;

import java.util.List;
import java.util.Objects;

public class Const implements AnyExpression {
    private int constNum;

    public Const(int num) {
        this.constNum = num;
    }

    @Override
    public int evaluate(int i) {
        return constNum;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return constNum;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return constNum;
    }

    @Override
    public String toString() {
        return String.valueOf(constNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return constNum == aConst.constNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(constNum);
    }
}