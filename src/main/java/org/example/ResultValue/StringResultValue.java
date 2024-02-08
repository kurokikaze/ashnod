package org.example.ResultValue;

public class StringResultValue implements ResultValue {
    private final String value;

    public StringResultValue(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
