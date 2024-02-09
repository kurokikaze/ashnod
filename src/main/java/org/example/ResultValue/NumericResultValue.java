package org.example.ResultValue;

public class NumericResultValue implements ResultValue {
    private final double value;
    private final String uom;

    public NumericResultValue(double value, String uom) {

        this.value = value;
        this.uom = uom;
    }

    public Double get() {
        return this.value;
    }
    public String getUnits() { return this.uom; }

    public String getType() { return "dec"; }
}
