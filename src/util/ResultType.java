package util;

public class ResultType <Type1, Type2>{
    private final Type1 value1;
    private final Type2 value2;

    public ResultType(Type1 value1, Type2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Type1 getValue1() {
        return value1;
    }

    public Type2 getValue2() {
        return value2;
    }
}
