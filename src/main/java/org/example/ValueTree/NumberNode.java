package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class NumberNode implements ValueNode {
    private final Integer value;

    public NumberNode(Integer value) {
        this.value = value;
    }

    @Override
    public NumericResultValue getValue(CalculationContext context) {
        return new NumericResultValue(this.value, "");
    }
}
