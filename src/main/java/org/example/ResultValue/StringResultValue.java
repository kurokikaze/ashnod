package org.example.ResultValue;

public class StringResultValue implements ResultValue {
    private final String value;
    private final String uom;

    public StringResultValue(String value, String uom) {

        this.value = value;
        this.uom = uom;
    }

    public String get() {
        return value;
    }

    @Override
    public String getUnits() { return this.uom; }

    @Override
    public String getType() { return "string"; }
}
