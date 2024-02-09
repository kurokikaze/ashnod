package org.example.ValueTree;

import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class NumberNode implements ValueNode {
    private final Integer value;

    public NumberNode(Integer value) {
        this.value = value;
    }

    @Override
    public NumericResultValue getValue(HashMap<String, ResultValue> variables) {
        return new NumericResultValue(this.value, "");
    }
}
