package org.example.ResultValue;

public class UndefinedResultValue implements ResultValue {
    @Override
    public Object get() {
        return null;
    }

    @Override
    public String getType() {
        return "undefined";
    }

    @Override
    public String getUnits() {
        return "";
    }
}
