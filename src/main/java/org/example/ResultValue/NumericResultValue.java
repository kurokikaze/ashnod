package org.example.ResultValue;

public class NumericResultValue implements ResultValue {
    private final int value;

    public NumericResultValue(int value) {
        this.value = value;
    }

    public Integer get() {
        return this.value;
    }
}
