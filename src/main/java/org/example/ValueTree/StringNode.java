package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.StringResultValue;

public class StringNode implements ValueNode {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public StringResultValue getValue(CalculationContext context) {
        return new StringResultValue(this.value, "");
    }
}
