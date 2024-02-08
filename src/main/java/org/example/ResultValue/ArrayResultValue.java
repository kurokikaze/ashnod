package org.example.ResultValue;

import java.util.ArrayList;

public class ArrayResultValue<T> implements ResultValue {
    private final ArrayList<T> value;

    public ArrayResultValue(ArrayList<T> value) {
        this.value = value;
    }

    @Override
    public ArrayList<T> get() {
        return this.value;
    }
}
