package org.example.ResultValue;

import java.util.ArrayList;

public class ArrayResultValue<T> implements ResultValue {
    private final ArrayList<T> value;
    private final String uom;

    public ArrayResultValue(ArrayList<T> value, String uom ) {
        this.value = value;
        this.uom = uom;
    }

    @Override
    public ArrayList<T> get() {
        return this.value;
    }

    public void add(T value) {
        this.value.add(value);
    }
    @Override
    public String getUnits() { return this.uom; }

    @Override
    // This should depend on the array content, this is just a placeholder
    public String getType() { return "dec"; }
}
